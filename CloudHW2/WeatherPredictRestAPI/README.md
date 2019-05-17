# Online Weather Forecast

[Online Weather Forecast](http://18.191.224.52:8080/WeatherPredictRestAPI/) is a web services application that provides simple GET/POST/DELETE methods to have basic CRUD operations and sends response in json form to user runs on AWS -EC2-LINUX2 instance.

## Steps Followed to Run Project in AWS

*   Created AWS EC2 LINUX2 instance following the instructions from AWS website. [Click Here](https://docs.aws.amazon.com/efs/latest/ug/gs-step-one-create-ec2-resources.html) 
*   Enabled the following ports for online weather forecast project:
    *   8080 (Tomcat)
    *   80   (HTTP)
    *   443  (HTTPS)
    *   22   (SFTP)
*   Installed Latest Java version by following the instructions in the video from [link](http://appsdeveloperblog.com/deploy-web-application-archivewar-amazon-aws-ec2-linux-instance/). Changed the system default java to latest installed java.
*   Installed LAMP (Linux, Apache, MySQL, phpMyAdmin) server for LINUX 2 following the instructions from the [link](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-lamp-amazon-linux-2.html)
    *   phpMyAdmin, MySQL are needed for this project and so we have enabled for remote access and started them.
*   Checked whether Apache is running or not.(after executing command -"sudo systemctl start httpd")
*   Installed Apache Tomcat to run Java - Spring Web Application by following the intructions from [link](http://appsdeveloperblog.com/deploy-web-application-archivewar-amazon-aws-ec2-linux-instance/) and used the default port 8080 
*   Installed and checked version of mvn in instance using following commands:
``` 
    sudo wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
    sudo sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
    sudo yum install -y apache-maven
    mvn --version
```
*   Installed java-jdk using the following commands and choose latest java from options to set.
```
    sudo yum install java-1.8.0-openjdk-devel
    sudo update-alternatives --config java
```
*   Now take sql script file [link](https://github.uc.edu/paladusn/CloudProjects/blob/master/WebservicesHW2-3/WeatherPredictRestAPI/initial.sql) and execute the queries in mysql using phpMyAdmin [link](http://18.191.224.52/phpMyAdmin) with root as username and sairam as password which are required for online weather forecast application.
*   Git clone or download the above WeatherPredictRestAPI project and execute the following commands to (build the project) get war file in target folder.
```
    cd WeatherPredictRestAPI/
    mvn clean package
```
*   Deployed my project by placing the war file obtained in target folder to /var/lib/tomcat/webapps/.

## Description for HW2

Online Weather Forecast is REST webservices applications with following operations and their descriptions:

*   GET /api/historical
      * When we hit the url, the json response will be recieved containing all dates in the system and sample response is as follows:
      ```
      url : http:AWSINSTANCE:8080/WeatherPredictRestAPI/api/historical
      method: get
      response: [{"DATE":"20130101"},{"DATE":"20130102"},....]
      ```
*   GET /api/historical/{date}
      * Here we have to pass the date in yyyymmdd format to get TMAX and TMIN values for particular date. Here date is passed in url so it accepts the input as string and response is in json form. Example is as follows:
      ```
      url : http:AWSINSTANCE:8080/WeatherPredictRestAPI/api/historical/20130103
      method: get
      response: {"DATE":"20130103","TMAX":90.0,"TMIN":23.0}
      ```
*   POST /api/historical
      * Here we have to pass the date in yyyymmdd format and TMAX and TMIN values which can be string or int or double for particular date. Here data is passed as json and response is in json form. If already date exists then record gets updated. Example is as follows:
      ```
      url: http:AWSINSTANCE:8080/WeatherPredictRestAPI/api/historical
      method: POST
      input (json):  {"DATE":"20200101","TMIN":33,"TMAX":44}
      response: {"DATE": "20200101","TMAX": 44,"TMIN": 33} with http status 201 (created)
      ```
*   DELETE /api/historical/{date}
      * Here we have to pass the date in yyyymmdd format to delete the record for particular date. Here date is passed in url so it accepts the input as string and response is in json form. Example is as follows:
      ```
      url: http:AWSINSTANCE:8080/WeatherPredictRestAPI/api/historical/20200101
      method: DELETE
      response: just http status 200 (created)
      ```
*   GET /api/forecast/{date}
      * Here we have to pass the date in yyyymmdd format to forecast the weather for next 7 days. If weather information is already available in database it is shown else using average we will forecaste weather for those days. Here date is passed in url so it accepts the input as string and response is in json form. Example is as follows:
      ```
      url : http:AWSINSTANCE:8080/WeatherPredictRestAPI/api/forecast/20180209
      method: get
      response: [
    {
        "DATE": "20180209",
        "TMAX": 33.7,
        "TMIN": 16.2
    },
    {
        "DATE": "20180210",
        "TMAX": 27.3,
        "TMIN": 12.6
    },
    {
        "DATE": "20180211",
        "TMAX": 30.5,
        "TMIN": 12.2
    },
    {
        "DATE": "20180212",
        "TMAX": 22.5,
        "TMIN": 9.6
    },
    {
        "DATE": "20180213",
        "TMAX": 23.8,
        "TMIN": 12.9
    },
    {
        "DATE": "20180214",
        "TMAX": 39.1,
        "TMIN": 22.9
    },
    {
        "DATE": "20180215",
        "TMAX": 41.6,
        "TMIN": 26.7
    }
    ]
    ```
## DATABASE Link
*   We can check all our transanaction in notes_app schema in weatherdates table in mysql database using phpMyAdmin [link](http://18.191.224.52/phpMyAdmin) with root as user and sairam as password.

## Swagger
*   Swagger can accessed using the url [link](http://18.191.224.52:8080/WeatherPredictRestAPI/swagger-ui.html):
```
url : http:AWSINSTANCE:8080/WeatherPredictRestAPI/swagger-ui.html 
```
## References:
* All the links mentioned till now can be considered as references.


