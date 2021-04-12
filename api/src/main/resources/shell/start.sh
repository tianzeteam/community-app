#!/bin/bash
nohup java -Djava.security.egd=file:/dev/./urandom -Dspring.elasticsearch.rest.uris=http://1.15.72.79:9200  -jar /home/app/community/server/controller-1.0-SNAPSHOT.jar --spring.profiles.active=test > /dev/null &
