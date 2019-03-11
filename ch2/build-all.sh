#!/bin/bash

cd coffee-drinks
mvn clean install

cd ../coffee-orders
mvn clean install
