
# Getting started

You can build the code for the Java projects by running the maven commands:

```sh
cd coffee-drinks
mvn clean install

cd ../coffee-orders
mvn clean install
```

There's also a script called `build-all.sh` which does the same.

## Command to build the images coffee-drinks, coffee-orders, and coffee-client

To build all the images using the default Dockerfile present in each of the projects:

```sh
docker-compose build
```


To build Docker images individually, you can navigate to each project folder root and run the docker build command.

```sh
cd coffee-drinks
docker build -t prashantpro/coffee-drinks .

cd coffee-orders
docker build -t prashantpro/coffee-orders .

cd coffee-client
docker build -t prashantpro/coffee-client .
```




Note: You should consider replacing "prashantpro" with your docker hub account id. If you intend to use this image to pull and publish to docker hub.


## Command to run the containers
(Note: It will build missing images first time, BUT will not rebuild images next time.)

```sh
docker-compose up
```

To kill the servers just press
CTRL+C

The below will rebuild the images and run the containers

```sh
docker-compose up --build
```

OR

To rebuild and run in background

```sh
docker-compose up --build -d
```


## To remove (and shutdown) the containers
```sh
docker-compose down
```

