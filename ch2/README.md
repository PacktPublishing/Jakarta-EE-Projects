
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

## Few curl commands to test the services

### Get all coffee types
This command will make a GET request to our `/coffees` resource. Which returns a JSON array.
```sh
⇒  curl -i http://localhost:8020/coffee-drinks/resources/coffees
HTTP/1.1 200 OK
...

[
  {
    "basePrice": 3.89,
    "type": "Flat White",
    "variantPrice": {
      "SMALL": 3.89,
      "LARGE": 8.89,
      "REGULAR": 5.89
    }
  },
  .
  .
]
```

### Get a particular coffee type
The next, curl will request a particular type of coffee using the request URI `/coffees/<coffee type>`.

```sh
⇒  curl -i http://localhost:8020/coffee-drinks/resources/coffees/Mocha
HTTP/1.1 200 OK
...

{
  "basePrice": 4.19,
  "type": "Mocha",
  "variantPrice": {
    "SMALL": 4.19,
    "LARGE": 9.19,
    "REGULAR": 6.19
  }
}
```

### Subscribe to Server-Sent events to listen for order updates
This can be opened in a new terminal window and kept running, so when we place orders from another terminal or client, we can see the updates showing up in this terminal.

```sh
⇒  curl -N --http2 -H "Accept:text/event-stream" \
http://localhost:8030/coffee-orders/resources/order-events

event: order
id: 2
data: {"created":"2019-03-16T10:56:20.243","customerEmail":"jack@example.org","drink":"Mocha","id":2,"status":"ACCEPTED"}

event: order
id: 2
data: {"created":"2019-03-16T10:56:20.243","customerEmail":"jack@example.org","drink":"Mocha","id":2,"status":"READY"}
```
### Place a coffee request (order) using HTTP POST  request.

```sh
⇒  curl -i \
	-H "Content-Type: application/json" \
	-X POST -d '{"email":"jack@example.org", "drink":"Mocha", "size": "REGULAR"}' \
	http://localhost:8030/coffee-orders/resources/orders

HTTP/1.1 202 Accepted
Location: http://localhost:8030/coffee-orders/resources/orders/3
...

{"order":3}
```

