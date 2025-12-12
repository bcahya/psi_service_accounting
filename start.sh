#!/bin/sh

cd /root/service.accounting
date=$(date "+%Y-%m-%d_%H%M%S")
echo "Stop App with kill existing PID..."
PID=$(cat pid.txt)
echo $PID
kill -9 $PID

echo "waiting to start!"
sleep 10

echo "Start app..."
nohup java -jar -Xms500m -Xmx4g /opt/backend_dashboard/k3pg.dashboard*.jar --spring.config.location=file:///root/service.accounting/app.properties > /root/service.accounting/log/log_"$date".txt &
PID=$!
echo $PID > pid.txt

sh clean.sh

exit 0
