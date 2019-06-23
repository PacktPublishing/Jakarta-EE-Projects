#!/bin/bash

cd movie-cloud
mvn clean install

cd ../recommendation
mvn clean install

cd ../
docker-compose build