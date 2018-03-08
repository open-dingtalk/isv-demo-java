#!/bin/sh
rm -rf /home/mint/antx.properties
kill -9 `ps -ef | grep tomcat | grep -v grep | awk '{print $2}'`
rm -rf /usr/local/soft/apache-tomcat-7.0.67/webapps/ding-isv-access.war
rm -rf /usr/local/soft/apache-tomcat-7.0.67/webapps/ding-isv-access
cp  /home/mint/workspace/ding-isv-access/web/target/ding-isv-access.war /usr/local/soft/apache-tomcat-7.0.67/webapps/
sh /usr/local/soft/apache-tomcat-7.0.67/bin/startup.sh
