#!/bin/bash
wait_shutdown_second=5
pid="`ps -ef | grep java | grep controller-1.0-SNAPSHOT.jar | awk '{print $2}'`"
echo "smart pid is ${pid}"

if [ -n "${pid}" ]; then
  kill -s 9 ${pid}
  sleep ${wait_shutdown_second}
  echo "smart stop successfully"
fi
echo "begin start smart"
cd /home/app/community
source ./start.sh &
echo "smart start successfully"
