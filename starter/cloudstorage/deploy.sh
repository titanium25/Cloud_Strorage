#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/ubuntu_20_server.pem \
    ~/IdeaProjects/Cloud_Strorage/starter/cloudstorage/target/cloudstorage-0.0.1-SNAPSHOT.jar \
    ubuntu@18.197.61.49:/home/ubuntu/

echo 'Restart server...'

ssh -i ~/.ssh/ubuntu_20_server.pem ubuntu@18.197.61.49 << EOF

pgrep java | xargs kill -9
nohup java -jar cloudstorage-0.0.1-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye'