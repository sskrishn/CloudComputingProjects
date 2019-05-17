# Hadoop Map Reduce Program in python

## Steps and Commands Followed to Run Program in University cluster

*   Connected to University Machine using Putty with **hostname:hadoop-gate-0.eecs.uc.edu**
*   Copied mapper.py and reducer.py to university machine using Winscp.
*   Now copied mapper.py and reducer.py from university machine to hadoop cluster using the following commands
```
    hadoop fs -put mapper.py .
    hadoop fs -put reducer.py .
```
*   Also taken nyc.data from /user/tatavag/nyc.data to local and moved back to my hadoop cluster
```
    hadoop fs -get /user/tatavag/nyc.data .
    hadoop fs -mkdir cloudInput
    hadoop fs -put nyc.data cloudInput/
```
*   Created directory to store hadoop output directory using the foloowing command
```
    hadoop fs -mkdir sskAssign1
```
*   After moving all required files to hadoop cluster use the following command to run the job:
```
    hadoop jar /usr/hdp/current/hadoop-mapreduce-client/hadoop-streaming.jar -file mapper.py -mapper "python mapper.py" -file reducer.py -     reducer "python reducer.py" -input /user/paladusn/cloudInput -output /user/paladusn/sskAssign1/ouputCloudFinalSubmissionfinal
```
*   After running the above command the logs will appear as in logs.txt file.

## Steps to clone and run the project in your cluster

```
    git clone https://github.uc.edu/paladusn/HadoopMapReduce.git
```
*   Just use the above commands to copy mapper.py and reducer.py to cluster and run the job using the command above specifying the paths input file and output files.


## References:
*   http://www.michael-noll.com/tutorials/writing-an-hadoop-mapreduce-program-in-python/
*   https://github.com/duf59/ud617


