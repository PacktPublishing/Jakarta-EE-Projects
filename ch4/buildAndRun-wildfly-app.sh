#!/bin/bash

mvn clean install -f spares/pom.xml help:active-profiles -P wildfly

docker-compose -f docker-compose-wildfly.yml up --build