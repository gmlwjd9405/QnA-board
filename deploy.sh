#!/bin/bash

TOMCAT_HOME=~/tomcat
PROJECT_HOME=~/QnA-board
DEPLOY_VERSION=qna-board-0.0.1-SNAPSHOT

# Load recently code
cd $PROJECT_HOME
git pull

# Build
./mvnw clean package

# Shutdown Tomcat Server
cd $TOMCAT_HOME/bin
./shutdown.sh

cd $TOMCAT_HOME/webapps
rm -rf ROOT

mv $PROJECT_HOME/target/$DEPLOY_VERSION $TOMCAT_HOME/webapps/ROOT/

# Start Tomcat Server
cd $TOMCAT_HOME/bin
./startup.sh

# Show log
tail -500f $TOMCAT_HOME/logs/catalina.out
