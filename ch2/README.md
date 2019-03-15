
# Getting started

You can build the code for the Java projects by running the maven commands from the `ch2` folder.

## Build backend
```sh
cd coffee-drinks
mvn clean install

cd ../coffee-orders
mvn clean install
```
> OR
There is a convinience script called `build-all.sh` which does the same.
Give execute permission Ex: $ `chmod u+x build-all.sh` and run this script.

## Command to build the images 

### 3 projects are Dockerized 
- coffee-drinks
- coffee-orders
- coffee-client

To build all the images using the default `Dockerfile` present in each of the projects, run below from `ch2` folder. (**Single command**)

```sh
docker-compose build
```
This builds all 3 images with the image name and port binding as defined in the `docker-compose.yml` file.
The images are build with the prefix `prashantpro/coffee-<name here>`. You can edit the YML file and replace 'prashantpro' with your docker hub user id. This allows you to push images to your own account.

>OR
To build Docker images individually, you can navigate to each project folder and run the docker build command.

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
`CTRL+C`

The below will rebuild the images and run the containers

```sh
docker-compose up --build
```

>OR
To rebuild and run in background
```sh
docker-compose up --build -d
```

## To remove (and shutdown) the containers
```sh
docker-compose down
```

Once all the images are running in containers, you can open the browser and load the coffee-client project.

```sh
http://localhost
```
