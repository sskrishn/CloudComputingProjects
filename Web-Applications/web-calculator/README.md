# Web Online Calculator

[Web Online Calulator](https://ec2-18-222-255-91.us-east-2.compute.amazonaws.com/web-calculator/) is a web application that provides simple calculator ui to have basic math operations which takes input from user and calculates results in server and send back to user runs on AWS -EC2-LINUX2 instance.

## Steps Followed to Run Project in AWS

*   Created AWS EC2 LINUX2 instance following the instructions from AWS website. [Click Here](https://docs.aws.amazon.com/efs/latest/ug/gs-step-one-create-ec2-resources.html) 
*   Enabled the following ports for web-calculator project:
    *   8080 (Tomcat)
    *   80   (HTTP)
    *   443  (HTTPS)
    *   22   (SFTP)
*   Installed Latest Java version by following the instructions in the video from [link](http://appsdeveloperblog.com/deploy-web-application-archivewar-amazon-aws-ec2-linux-instance/). Changed the system default java to latest installed java.
*   Installed LAMP (Linux, Apache, MySQL, phpMyAdmin) server for LINUX 2 following the instructions from the [link](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-lamp-amazon-linux-2.html)
    *   phpMyAdmin, MySQL are not needed for this web-caculator project.
*   Checked whether Apache is running or not.(after executing command -"sudo systemctl start httpd")
*   Configured the Apache to use SSL/TLS using the [link](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/SSL-on-an-instance.html)
    *   CA-signed Certificate part from the link has not been done.
*   Installed Apache Tomcat to run Java - Spring Web Application by following the intructions from [link](http://appsdeveloperblog.com/deploy-web-application-archivewar-amazon-aws-ec2-linux-instance/)and used the default port 8080 
*   Redirected all http request to https. Also redirected requests from apache to apache tomcat by creating dummy.conf file apache httpd folder as follows:

         <VirtualHost *:80>
            ServerName www.ec2-******.us-east-2.compute.amazonaws.com
            ServerAlias ec2-******.us-east-2.compute.amazonaws.com
            DocumentRoot /var/www/html
         Redirect permanent / https://ec2-***********.us-east-2.compute.amazonaws.com/
         </VirtualHost>

         <VirtualHost *:443>
            ServerName www.ec2-*********.us-east-2.compute.amazonaws.com
            ServerAlias ec2-*********.us-east-2.compute.amazonaws.com
            DocumentRoot /var/www/html

         SSLEngine on
	      SSLCertificateFile /etc/pki/tls/certs/localhost.crt
	      SSLCertificateKeyFile /etc/pki/tls/private/localhost.key

   
         SSLProxyEngine on
         ProxyPass /web-calculator/cal http://localhost:8080/web-calculator/cal
         ProxyPassReverse /web-calculator/cal http://localhost:8080/web-calculator/cal
	
	      SSLProxyEngine on
         ProxyPass /web-calculator http://localhost:8080/web-calculator
         ProxyPassReverse /web-calculator http://localhost:8080/web-calculator
	
	      SSLProxyEngine on
         ProxyPass /web-calculator/home http://localhost:8080/web-calculator/home
         ProxyPassReverse /web-calculator/home http://localhost:8080/web-calculator/home
	
         </VirtualHost>
         
*   Deployed my project by connecting to instance and placing the war file /var/lib/tomcat/webapps/.

#### Note
*   Take .war file from my github [link](https://github.com/sskrishn/CloudProjects/tree/master/Web-Applications/web-calculator/target) to run web-calculator in your instance and keep 

## Usage of Website
*   Enter numbers and operations in correct order and click on result to get the result for given equation.
