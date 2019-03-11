#!/usr/bin/env bash
mvn clean package && docker build -t prashantpro/coffee-orders .
docker rm -f coffee-orders || true && docker run -d -p 8080:8080 --name coffee-orders prashantpro/coffee-orders
