#!/bin/bash

docker build . -t prashantpro/product-catalogs:1.0
docker run --rm -p 9080:9080 -p 9443:9443 prashantpro/product-catalogs:1.0
