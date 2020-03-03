# Deploy Spring boot with Nginx on ubuntu

apt update
apt upgrade
apt install software-properties-common

apt install ufw nginx
sudo systemctl start ufw
sudo systemctl enable ufw
sudo ufw enable # if systemctl enable ufw doesn't work

ufw app list
ufw allow 'Nginx HTTP'
ufw allow 'OpenSSH' 
ufw enable
sudo systemctl start nginx
sudo systemctl enable nginx

# make directory for foodb 
mkdir /apps

# name the APPNAME
export APPNAME=<appname>
sudo /usr/sbin/useradd -d /apps/$APPNAME -m $APPNAME -s /bin/bash
sudo passwd $APPNAME
sudo su $APPNAME
ssh-keygen -t rsa

sudo su $APPNAME
mkdir ~/project

# copy everything to ~/project directory

sudo chown $APPNAME:$APPNAME your-app.jar
sudo chmod 500 your-app.jar



running spring boot app as background serve
# add file to directory  /etc/systemd/system/ and name is *****.service
[Unit]
Description= # Place a descriptive application name here
After=syslog.target
After=network.target[Service]
User= # Define a user account that will own our app
Type=simple

[Service]
ExecStart=/usr/bin/java -jar # Provide /path/to/file/myapplication.jar
Restart=always
StandardOutput=syslog
StandardError=syslog
SyslogIdentifier= # A short identifier for system journal, f. e. 'myapplication'

[Install]
WantedBy=multi-user.target


# start application
# This will start service from file we created earlier
sudo systemctl start myapplication.service
# To see if it's running we can check system journal
journalctl -u myapplication -b

# Configuring NGINX to proxy requests
# edit the file in /etc/nginx/sites-available/ (mostly like the file name is "default" on ubuntu, but it may be different on other os)
# make below changes
server {
        # NGINX will listen on port 80 for both IP V4 and V6
        listen 80;
        listen [::]:80;

        # Here we should specify the name of server
        server_name myserver.com;

        # Requests to given location will be redirected
        location /myapplication {
        
             # NGINX will pass all requests to specified location here
             proxy_pass http://localhost:8080/;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
             proxy_set_header X-Forwarded-Proto $scheme;
             proxy_set_header X-Forwarded-Port $server_port;
        }
}

# reload nginx config file
sudo nginx -t
# restart NGINX service
sudo systemctl restart nginx




# issue:
# if running spring boot at background is not stable, try running it at non-root user with java -jar *.jar 

# if springboot service is not running
# then the ip address will be 502 Bad Gateway


# if running CDK with non-root user, you need to install native inchi library from source under root
# this is work-around solution 
# more reference please see https://github.com/cdk/cdk/issues/534#issuecomment-508263365
# install svn (yes, it is pretty old)
sudo apt install subversion
svn checkout https://svn.code.sf.net/p/jni-inchi/code/trunk jni-inchi-code
cd jni-inchi-code
# find java home /usr/lib/jvm/ add to /etc/environment
# add following line
JAVA_HOME="/usr/lib/jvm/openjdk"
export JAVA_HOME
# remember to restart the terminal to make it work
# define the system as LINUX (OR WINDOWS,MACOS, see the Makefile)
PLATFORM="LINUX"
export PLATFORM
# lastly
# do make all
metnet@ubuntu:~/jni-inchi-code$ make all
# haven't test it 



##################################################
instructions below is particularly for MetabolismNetwork


Complete install for new server
sudo apt update
sudo apt upgrade
sudo apt install openjdk-8-jre
sudo apt install openjdk-8-jdk

sudo apt install software-properties-common
sudo apt install nginx


# install net-tools
sudo apt install net-tools
# enable ufw
sudo ufw enable
# check available nginx package
sudo ufw app list
# allow the port 80 for nginx
sudo ufw allow 'Nginx HTTP'
sudo ufw allow 'OpenSSH'


# configure reverse proxy
# edit nginx configuration file /etc/nginx/conf.d/new_config_file_name.conf

# restart nginx
sudo nginx -t
sudo systemctl restart nginx


# if jni-inchi is required in your program
# install jni-inchi to enable CDK inchi module under production mode
# install svn version control system
svn checkout https://svn.code.sf.net/p/jni-inchi/code/trunk jni-inchi-code

cd jni-inchi-code

# define the JAVA_HOME and PLATFORM
# for linux
export JAVA_HOME=location/to/jdk
export PLATFORM=LINUX

# inside jni-inchi-code
# do
mvn install
# the maven local repo for jni-inchi will be installed in ~/.m2/repository/net/sf/

# git clone cdk.git
# change the pom.xml of cdk to the local jni-inchi repo
# then do mvn install
mvn install
# the maven local repo for cdk is located at /.m2/repository/org/openscience/cdk/cdk-bundle/

# open spring suite and modify the <dependency> for cdk-bundle.
# then do mvn package in MetabolismNetwork root directory

# do package
make jar # will create jar at target folder (deeper)

# create local repo
mvn install:install-file -Dfile=target/native/1.03_1-LINUX/jniinchi-1.03_1-LINUX.jar -Dpackaging=jar -DgeneratePom=true -DgroupId=net.sf.jni-inchi -DartifactId=jni-inchi -Dversion=0.9-danissss
# modify cdk pom.xml to make jni-inchi point to local version
# then do 
mvn install # in cdk root 


# compile the MetabolismNetwork with local cdk installed
# modify the pom.xml file



























































