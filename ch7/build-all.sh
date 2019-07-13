#!/bin/bash

cd movie-cloud
mvn clean install
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/movie-cloud-jvm:1 .

cd ../recommendation
mvn clean install
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/recommendation-jvm:1 .
