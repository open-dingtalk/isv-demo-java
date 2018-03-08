#!/bin/sh
rm -rf /home/mint/antx.properties
kill -9 `ps -ef | grep tomcat | grep -v grep | awk '{print $2}'`
rm -rf /usr/local/soft/apache-tomcat-7.0.67/webapps/ding-isv-accesss.war
rm -rf /usr/local/soft/apache-tomcat-7.0.67/webapps/ding-isv-accesss
cp  /home/mint/workspace/ding-isv-access/web/target/ding-isv-accesss.war /usr/local/soft/apache-tomcat-7.0.67/webapps/
sh /usr/local/soft/apache-tomcat-7.0.67/bin/startup.sh
