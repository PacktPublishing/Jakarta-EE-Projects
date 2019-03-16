#!/usr/bin/env bash
mvn clean package && docker build -t prashantpro/coffee-drinks .
docker rm -f coffee-drinks || true && docker run -d -p 8020:8080 --name coffee-drinks prashantpro/coffee-drinks
