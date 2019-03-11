#!/usr/bin/env bash
#deploy="false"
deploy="true"

image=coffee-orders
version=1.0-SNAPSHOT
latest=true

#OPTIONS="--no-cache --force-rm"
#OPTIONS="--no-cache"
#OPTIONS="--force-rm"
OPTIONS=""

docker build ${OPTIONS} -t prashantpro/${image}:1.0-SNAPSHOT .
if [ "$?" -eq 0 ] && [ ${deploy} == "true" ]; then
    docker push prashantpro/${image}:1.0-SNAPSHOT
    if [ "$latest" == "true" ]; then
        docker tag prashantpro/${image}:1.0-SNAPSHOT prashantpro/${image}:latest
        docker push prashantpro/${image}:latest
    fi
fi