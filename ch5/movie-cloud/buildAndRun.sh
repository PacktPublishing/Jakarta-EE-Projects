#!/bin/sh
mvn clean package && docker build -t org.jakartaeeprojects/movie-cloud:1 .
docker rm -f movie-cloud || true && docker run -d -p 8080:8080 --name movie-cloud org.jakartaeeprojects/movie-cloud:1
