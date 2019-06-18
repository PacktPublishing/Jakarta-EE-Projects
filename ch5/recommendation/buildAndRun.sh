#!/bin/sh
mvn clean package && docker build -t org.jakartaeeprojects/recommendation:1 .
docker rm -f recommendation || true && docker run -d -p 8080:8080 --name recommendation org.jakartaeeprojects/recommendation:1
