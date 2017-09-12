#!/bin/sh
rm -rf $1/webapps/ding-isv-access.war
rm -rf $1/webapps/ding-isv-access
cp  ./target/ding-isv-access.war $1/webapps/
echo kill `ps aux |grep "tomcat"|grep "java"|grep -v grep|tr -s ' '|cut -d' ' -f2`
ps aux |grep "tomcat"|grep "java"|grep -v grep|tr -s ' '|cut -d' ' -f2|xargs kill -9
sh $1/bin/startup.sh
