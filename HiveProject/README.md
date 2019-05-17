# BigData Analysis Project - Weather using Hive
### Introduction:
*   In this project we will have to perform Data Analysis and processing using MapReduce/ Apache Spark / Hive. The Project will use the weather dataset from https://www1.ncdc.noaa.gov/pub/data/ghcn/daily/by_year/ .This project will use only 19 years of data ( 2000 - 2019) for all the stations starting with US and elements TMAX, TMIN. The dataset is available on the CEAS hadoop directory (UC Cluster) /user/tatavag/weather.

*   Else the dataset can be downloaded using the following script
```
    for i in `seq 2000 2019`
    do
    wget https://www1.ncdc.noaa.gov/pub/data/ghcn/daily/by_year/${i}.csv.gz
    gzip -cd ${i}.csv.gz | grep -e TMIN -e TMAX | grep ^US > ${i}.csv
    done
```
### Data Understanding:
Each field described below is separated by a comma ( , ) and follows the order presented in this document.
*   ID = 11 character station identification code
*   YEAR/MONTH/DAY = 8 character date in YYYYMMDD format (e.g. 19860529 = May 29, 1986)
*   ELEMENT = 4 character indicator of element type
*   DATA VALUE = 5 character data value for ELEMENT
*   M-FLAG = 1 character Measurement Flag
*   Q-FLAG = 1 character Quality Flag
*   S-FLAG = 1 character Source Flag
*   OBS-TIME = 4-character time of observation in hour-minute format (i.e. 0700 =7:00 am)

Also, See section III of the GHCN-Daily [readme.txt](ftp://ftp.ncdc.noaa.gov/pub/data/ghcn/daily/readme.txt) file for an explanation of ELEMENT codes and their units as well as the M-FLAG, Q-FLAGS and S-FLAGS. The OBS-TIME field is populated with the observation times contained in NOAA/NCDC’s Multinetwork Metadata System (MMS).

#### Note:
*   After reading readme.txt, data is valid or it did not fail any quality assurance check only when Q_FLAG is blank assuming null and blank as different. Also data with data_value = -9999 or 9999 are noise and so we will neglect them. So final conditions we impose are data_value not equals to -9999 or 9999 and q_flag='';

## Steps and Queries followed:
*   Move the data downloaded to hadoop cluster and keep the path (as P1).
#### Creating table with all fields as 'station':
*   Here location path should be data path in hadoop.
```
CREATE EXTERNAL TABLE IF NOT EXISTS station(
id varchar(11),
date_yyyymmdd varchar(8),
element varchar(4),
data_value bigint,
m_flag varchar(1),
q_flag varchar(1),
s_flag varchar(1),
obs_time varchar(4)
)
COMMENT 'Data about weather'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
location '/user/cloudera/weather_data/';
```
### Analysis:
*   Every query results are stored in a new table so we can retrieve results from result's table instead of running heavy computation query again and again.
#### 1. Average TMIN, TMAX for each year excluding abnormalities or missing data
*   Results can be seen in Avg_Tmin_Tmax.xlsx file.
```
CREATE TABLE year_wise_avg AS
SELECT substring(date_yyyymmdd,1,4) as YEAR,element,avg(data_value)/10 Average from station where element in ('TMIN','TMAX') and q_flag='' and data_value<> -9999 and data_value<> 9999  GROUP BY substring(date_yyyymmdd,1,4),element ORDER BY substring(date_yyyymmdd,1,4),element;

SELECT * FROM year_wise_avg ORDER BY year,element;
```
#### 2.a. Max TMAX for each year excluding abnormalities or missing data
*   Results can be seen in Max_Tmax.xlsx file.
```
CREATE TABLE year_wise_max AS
SELECT substring(date_yyyymmdd,1,4) as YEAR,element,max(data_value)/10 Maximum from station where element in ('TMAX') and q_flag='' and data_value<> -9999 and data_value<> 9999  GROUP BY substring(date_yyyymmdd,1,4),element ORDER BY substring(date_yyyymmdd,1,4);

SELECT * FROM year_wise_max ORDER BY year;
```
#### 2.b. Min TMIN for each year excluding abnormalities or missing data
*   Results can be seen in Min_Tmin.xlsx file.
```
CREATE TABLE year_wise_min AS
SELECT substring(date_yyyymmdd,1,4) as YEAR,element,min(data_value)/10 Minimum  from station where element in ('TMIN') and q_flag='' and data_value<> -9999 and data_value<> 9999  GROUP BY substring(date_yyyymmdd,1,4),element ORDER BY substring(date_yyyymmdd,1,4);

SELECT * FROM year_wise_min ORDER BY year;
```
#### 3.a. Five hottest observations and their weather stations for each year excluding abnormalities or missing data
*   Results can be seen in five_coldest_yearwise.xlsx file.
```
CREATE TABLE year_wise_5_hottest_observations AS
SELECT substring(date_yyyymmdd,1,4) as year,rnk,id,date_yyyymmdd,element,data_value/10 as data_value FROM
			(
				SELECT dense_rank() OVER(PARTITION BY substring(date_yyyymmdd,1,4) order by data_value desc) rnk,* FROM station WHERE element='TMAX' and q_flag=''  and data_value<>-9999 and data_value<> 9999 
			)tmp WHERE rnk<=5 order by year,rnk;	
SELECT * FROM year_wise_5_hottest_observations;
```
#### 3.b. Five coldest observations and their weather stations for each year excluding abnormalities or missing data
*   Results can be seen in five_coldest_yearwise.xlsx file.
```
CREATE TABLE year_wise_5_coldest_observations AS
SELECT substring(date_yyyymmdd,1,4) as year,rnk,id,element,data_value/10 as data_value FROM
			(
				SELECT dense_rank() OVER(PARTITION BY substring(date_yyyymmdd,1,4) ORDER BY data_value asc) rnk,* FROM station WHERE element='TMIN' and q_flag='' and data_value<>-9999 and data_value<> 9999 
			)tmp WHERE rnk<=5 order by year,rnk	
		
SELECT * FROM year_wise_5_coldest_observations;
```
#### 4.a. Hottest Day and corresponding weather station in entire dataset
```
CREATE TABLE hottest_day_station AS
SELECT * FROM station WHERE element='TMAX' ORDER BY data_value desc limit 1;
select * from hottest_day_station;
```
##### Result: 
```
id		|	date_yyymmdd 	|	element |	data_value	|m_flag	|	q_flag	|	s_flag	| obs_time
USC00043161 	|	20090217 	|	TMAX	|	55372		|	|	X 	|	0	| 1600	
```
#### Note:
* 1) Here if we run the query on entire dataset we observed that the maximum value is 55372 which is invalid(55372/10=5537.2) but as it is highest we got it as hottest day. So we ignore invalid data and consider valid data using q_flag we ran below query:
```	
CREATE TABLE hottest_day_station_noabnorm AS
SELECT id,date_yyyymmdd,element, (data_value/10) as data_value,m_flag,q_flag,s_flag,obs_time FROM station WHERE element='TMAX' and q_flag='' and data_value<>-9999 and data_value<> 9999 ORDER BY data_value desc limit 1;

select * from hottest_day_station_noabnorm;

```
##### Result:
```
id		|	date_yyymmdd 	|	element |	data_value	|m_flag	|	q_flag	|	s_flag	| obs_time
USR0000HKAU 	|	20150213 	|	TMAX	|	55.6		|H	|	 	|	U	| 	
```
* 2) Still, if we want to consider all data but not just valid data_values we can use the following query:
*    Results will be in hottest_entireDataset.xlsx file.
```	
CREATE TABLE hottest_day_station_alldata AS
SELECT id,date_yyyymmdd,element, (data_value/10) as data_value,m_flag,q_flag,s_flag,obs_time FROM station where data_value in (select data_value from station WHERE element='TMAX' and data_value>=-999 and data_value<= 999 ORDER BY data_value desc limit 1) and element='TMAX';
select * from hottest_day_station_alldata;
```
#### 4.b. Coldest Day and corresponding weather station in entire dataset
```
CREATE TABLE coolest_day_station AS
SELECT * FROM station WHERE element='TMIN' ORDER BY data_value asc limit 1;
select * from coolest_day_station;
```
##### Result: 
```
id		|	date_yyymmdd 	|	element |	data_value	|m_flag	|	q_flag	|	s_flag	| obs_time
USS0050S01S 	|	20060519 	|	TMIN	|	-5738		|	|	X 	|	T	| 	
```
#### Note:
* 1) Here if we run the query on entire dataset we observed that the inimum value is -5738 which is invalid(-5738/10=-573.8) but as it is lowest we got it as coldest day. So we ignore invalid data and consider valid data using q_flag we ran below query:
```	
CREATE TABLE coolest_day_station_noabnorm AS
SELECT id,date_yyyymmdd,element, (data_value/10) as data_value,m_flag,q_flag,s_flag,obs_time FROM station WHERE element='TMIN' and q_flag='' and data_value<>-9999 and data_value<> 9999 ORDER BY data_value asc limit 1;

select * from coolest_day_station_noabnorm;
```
##### Result:
```
id		|	date_yyymmdd 	|	element |	data_value	|m_flag	|	q_flag	|	s_flag	| obs_time
USC00501684 	|	20000101 	|	TMIN	|	-57.8		|H	|	 	|	0	| 0800	
```
* 2) Still, if we want to consider all data but not just valid data_values we can use the following query:
*    Results will be in coldest_entireDataset.xlsx file.
```	
CREATE TABLE coolest_day_station_alldata AS
SELECT id,date_yyyymmdd,element, (data_value/10) as data_value,m_flag,q_flag,s_flag,obs_time FROM station where data_value in (select data_value from station WHERE element='TMIN' and data_value>=-999 and data_value<= 999 ORDER BY data_value asc limit 1) and element='TMIN';
select * from coolest_day_station_alldata;
```
## BONUS
#### 6.a. Median TMIN for the entire dataset 
```
SELECT median/10 as median from (SELECT percentile(data_value,0.5) median FROM station WHERE element in ('TMIN')) tmp;
```
##### Results:
```
|median|
|5.0   |
```
#### 6.b. Median TMAX for the entire dataset 
```
SELECT median/10 as median from (SELECT percentile(data_value,0.5) median FROM station WHERE element in ('TMAX')) tmp;
```
##### Results:
```
|median|
|18.5  |
```
## Software Used:
* Here I have used cloudera vm in my local machine and directly ran queries in Impala engine http://quickstart.cloudera:8888/hue/.
* Please find the screenshot file hue.PNG from above files to have a reference.
* Used Hive from cloudera.
### About Hive:
Apache Hive is a data warehouse software project built on top of Apache Hadoop for providing data query and analysis. Hive gives a SQL-like interface to query data stored in various databases and file systems that integrate with Hadoop.
### About Impala:
Apache Impala is an open source massively parallel processing SQL query engine for data stored in a computer cluster running Apache Hadoop. Impala has been described as the open-source equivalent of Google F1, which inspired its development in 2012.
### WHY HIVE?
I am familiar with SQL queries and given assignment can be solved using queries with less effort. 
Apache hive can be used for below reasons:
   1. Fits the low level interface requirement of Hadoop perfectly.
   2. Supports external tables which make it possible to process data without actually storing in HDFS.
   3. It has a rule based optimizer for optimizing logical plans.
   4. Supports partitioning of data at the level of tables to improve performance.
   5. Metastore or Metadata store is a big plus in the architecture which makes the lookup easy.
   6. Ad hoc quering is easier
   Also, used Impala engine to runqueries as it is faster than Hive in query processing. While processing SQL-like queries, Impala does not write intermediate results on disk; instead full SQL processing is done in memory, which makes it faster.
   
### References:
	* https://en.wikipedia.org/wiki/Apache_Hive
	* https://www.quora.com/Why-should-you-use-Apache-Hive
	* https://en.wikipedia.org/wiki/Apache_Impala
	* https://www.cloudera.com/products/open-source/apache-hadoop/key-cdh-components.html
	* https://www.quora.com/Why-should-you-use-Apache-Hive
