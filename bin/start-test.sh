#!/bin/bash
CURR_DIR=`dirname $0`
cd $CURR_DIR
if [ ! -d "../logs" ]; then
  mkdir ../logs
fi
nohup java -jar ../base-pdf-1.0.0-RELEASE.jar --server.port=8666 --spring.profiles.active=test 1>/dev/null 2>> ../logs/nohup-err.out &
