#!/bin/bash

cd product-catalogs
mvn clean install

cd ../product-ads
mvn clean install
