# Online Weather Forecast

[Online Weather Forecast](http://18.191.224.52:8080/WeatherPredictRestAPI/uipage.html) is a weather forecast application that forecast the weather for 7 days from selected data.

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
*   Now take sql script file [link](https://github.uc.edu/paladusn/CloudProjects/blob/master/WebservicesHW2-3/WeatherPredictRestAPI/initial.sql) and execute the queries in mysql using phpMyAdmin [link](http://18.191.224.52/phpMyAdmin) which are required for online weather forecast application.
*   Git clone or download the above WeatherPredictRestAPI project and execute the following commands to (build the project) get war file in "target" folder inside WeatherPredictRestAPI folder.
```
    cd WeatherPredictRestAPI/
    mvn clean package
```
*   Deployed my project by placing the war file obtained in target folder to /var/lib/tomcat/webapps/.

## DATABASE Link
*   We can check all our transanaction in notes_app schema in weatherdates table in mysql database using phpMyAdmin [link](http://18.191.224.52/phpMyAdmin) with root as user and sairam as password.

## Description for HW3
*   Select a valid date and click on predict to forecast the weather using above api and also from third party api (darksky) for 7 days.[Link](http://18.191.224.52:8080/WeatherPredictRestAPI/uipage.html)
*   Here I have followed [link](https://darksky.net/dev/docs) to access the darksky api to forecast weather for 7 days for given date in my ajax which is in myscript.js file in above project. (Max limit of hits for third party api is 1000 per day)
*   First section show the forecasted values using average method from own api whereas second section shows the forecasted values obtained from third party api.
*  Also up arrow indicates "tmax" and down arrow indicates "tmin"
```
url : http:AWSINSTANCE:8080/WeatherPredictRestAPI/uipage.html
```

## References:
* All the links mentioned till now can be considered as references.


