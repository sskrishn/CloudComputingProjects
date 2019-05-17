# Dockerizing Online Weather Forecast Application

## Steps Followed to Run Docker Project

*   Created AWS EC2 LINUX2 instance following the instructions from AWS website. [Click Here](https://docs.aws.amazon.com/efs/latest/ug/gs-step-one-create-ec2-resources.html) 
*   Installed docker using the following commands
``` 
    sudo yum update -y
    sudo yum install -y docker
    sudo service docker start     (or)  sudo systemctl start tomcat
    sudo usermod -a -G docker ec2-user
```
*   Now exit the terminal to have new changes reflected.
*   Now install the docker-compose using the following commands:
```
    sudo curl -L https://github.com/docker/compose/releases/download/1.22.0/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
    docker-compose version
```
*   Now clone the above project weather-dockercompose-master using the following command and then download .m2 (repository for jars) for this project using drive [link](https://drive.google.com/file/d/1TzL7MHd8eSbF-eUgNMp2HpfFRKSS1GNE/view?usp=sharing)
```
git clone https://github.uc.edu/paladusn/CloudProj1.git
```
*   Unzip the .m2 and keep the .m2 folder in above project parallel to docker compose file.
*   Now kill the services that are are running on 80, 8080 and 3306 in host machine
*   Now using the following command run the docker compose file
```
    docker-compose up
```
*   Now you can hit the url YOURHOSTIP/api/historical to access the 'get' request or YOURHOSTIP/uipage.html

*   Extra steps to restart docker-compose project
```
    docker-compose stop
    docker-compose rm
```
## Simple step to run project
*   Download the entire project using the [link](https://drive.google.com/file/d/1ELhl_auiIzu8w_zk5lB-GKMzVHraDYRr/view?usp=sharing) and after installing docker compose run the following command inside weather-dockercompose-master folder 
```
    docker-compose up
```
* Here the url of application will be in  format --> http://YOURIP/api/historical  [HW2] (or)   http://YOURIP/uipage.html [HW3] 
## Description

*   As we are using MySQL, Tomcat (Spring boot embedded tomcat) and ngnix multiple servers so we have used docker compose which will use base images and link them using docker compose.
*   If we want to change the ports other than default  we can change them in docker-compose.yaml file
*   'db' folder contains all the intial database scripts that are required to run initially. Once docker-compose is up, mysql image will run in background and so we stop docker compose bu using "CTRL+C" we will have our scripts in mysql and so if we run it for second time the scripts will not executed.
*   HW2 and HW3 applications will be up once if run docker compose.
*   Rest api can be access using the url in format -- http://YOURINSTANCEIP/api/historical or  http://YOURINSTANCEIP/api/forecast/20130101
*   In uipage we can select a date to forecast the data for 7 days. uipage url will be - http://YOURINSTANCEIP/uipage.html
*   Select a valid date and click on predict to forecast the weather using above api and also from third party api (darksky) for 7 days.
*   Here I have followed [link](https://darksky.net/dev/docs) to access the darksky api to forecast weather for 7 days for given date in my ajax which is in myscript.js file in above project. (Max limit of hits for third party api is 1000 per day)
*   First section show the forecasted values using average method from own api whereas second section shows the forecasted values obtained from third party api.
*  Also up arrow indicates "tmax" and down arrow indicates "tmin"
  

## References
* https://docs.docker.com/compose/

