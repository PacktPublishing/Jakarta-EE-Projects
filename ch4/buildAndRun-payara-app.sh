#!/bin/bash

mvn clean install -f spares/pom.xml help:active-profiles -P payara-micro

docker-compose -f docker-compose.yml up --build