
# Getting started

You can build the code for the projects by running the commands from the `ch3` folder.

## Building the services with one command
From the ch3 folder, execute the script file, which is a convinience script, called `build-all.sh`

```sh
./build-all.sh
```

You may need to give execute permission Ex: $ `chmod u+x build-all.sh` and run this script.

## Building the services individually
```sh
cd product-catalogs
mvn clean install

cd ../product-ads
mvn clean install
```

## Building and running with Docker

### Docker images

To build all the images using the default `Dockerfile` present in each of the projects, run below from `ch3` folder. (**Single command**)

```sh
docker-compose build
```
This builds both images with the image name and port binding as defined in the `docker-compose.yml` file.
The images are build with the prefix `prashantpro/product-<catalogs or ads>`. You can edit the YML file and replace 'prashantpro' with your docker hub user id. This allows you to push images to your own account.

>OR
To build Docker images individually, you can navigate to each project folder and run the docker build command.

```sh
cd product-catalogs
docker build . -t prashantpro/product-catalogs:1.0

cd product-ads
docker build . -t prashantpro/product-ads:1.0
```

Note: You should consider replacing "prashantpro" with your docker hub account id. If you intend to use this image to pull and publish to docker hub.

## Command to run the containers without Prometheus
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
## Command to run the containers with Prometheus
(Note: This is mostly the same commands, but we just specify a different compose file.)

```sh
docker-compose -f docker-compose-prom.yml up
```

To kill the servers just press
`CTRL+C`

Open Prometheus URL by visiting this link

```sh
http://localhost:9090
```

## To remove (and shutdown) the containers
```sh
docker-compose -f  docker-compose-prom.yml down
```

Once all the images are running in containers, you can open the browser and load few REST API urls to try out.

```sh
http://localhost:9080/product-catalogs/resources/books
http://localhost:9080/product-catalogs/resources/authors
http://localhost:8080/resources/ads/Programming
```

## Few curl commands to test the services

### Get all books
This command will make a GET request to our `/books` resource. Which returns a JSON array.
```sh
⇒  curl -i http://localhost:9080/product-catalogs/resources/books
HTTP/1.1 200 OK
...

[
  {
    "author": "Prashant",
    "category": "Programming",
    "id": 2,
    "isbn10": "1539412004",
    "isbn13": "9781539412004",
    "price": 400,
    "reviewCount": 3,
    "status": "AVAILABLE",
    "title": "Java EE 8 and Angular"
  },
  .
  .
]
```

### Get limited books using pagination params

```sh
⇒  curl -i http://localhost:9080/product-catalogs/resources/books\?limit\=2
HTTP/1.1 200 OK

[
  {
    "author": "Prashant",
    "category": "Programming",
    "id": 2,
    "isbn10": "1539412004",
    "isbn13": "9781539412004",
    "price": 400,
    "reviewCount": 3,
    "status": "AVAILABLE",
    "title": "Java EE 8 and Angular"
  },
  {
    "author": "Prashant",
    "category": "Programming",
    "id": 6,
    "isbn10": "1539412005",
    "isbn13": "9781539412005",
    "price": 500,
    "reviewCount": 1,
    "status": "OUT_OF_STOCK",
    "title": "Jakarta EE Projects"
  }
]
```

### Get a particular book
The next, curl will request a particular book using the request URI `/book/<id>`.
Note, the id being passed needs to exist.

```sh
⇒  curl -i http://localhost:9080/product-catalogs/resources/books/2
HTTP/1.1 200 OK
...

{
  "adList": [
    {
      "desc": "Jump on board and work at lightspeed",
      "link": "https://openliberty.io"
    },
    {
      "desc": "Optimised for microservice architectures",
      "link": "https://thorntail.io"
    }
  ],
  "author": "Prashant Padmanabhan",
  "category": "Programming",
  "id": 2,
  "isbn10": "1539412004",
  "isbn13": "9781539412004",
  "price": 400,
  "reviewCount": 3,
  "reviews": [
    "Awesome book",
    "Good choice",
    "Too technical"
  ],
  "status": "AVAILABLE",
  "title": "Java EE 8 and Angular"
}
```

### Get authors
This command will make a GET request to our `/authors` resource. Which returns a JSON array.
```sh
⇒  curl -i http://localhost:9080/product-catalogs/resources/authors
HTTP/1.1 200 OK
...

[
  {
    "email": "prashantp@xyz.org",
    "firstName": "Prashant",
    "id": 1,
    "lastName": "Padmanabhan"
  },
  .
  .
]
```

Similar to /books resource you can pass pagination params such as start and limit.

```sh
⇒  curl -i http://localhost:9080/product-catalogs/resources/authors\?limit\=2
HTTP/1.1 200 OK
...

[
  {
    "email": "prashantp@xyz.org",
    "firstName": "Prashant",
    "id": 1,
    "lastName": "Padmanabhan"
  },
  .
  .
]
```

### Get a particular author
```sh
⇒  curl -i http://localhost:9080/product-catalogs/resources/authors/1
HTTP/1.1 200 OK

{
  "email": "prashantp@xyz.org",
  "firstName": "Prashant",
  "id": 1,
  "lastName": "Padmanabhan"
}
```

### Add new book using HTTP POST  request.

```sh
⇒  curl -i \
	-H "Content-Type: application/json" \
	-X POST -d '{"title":"Java EE 8 and Angular", "category":"Programming", "status": "AVAILABLE", "price": "900.00", "author": {"email": "prashantp@xyz.org"}}' \
	http://localhost:9080/product-catalogs/resources/books

HTTP/1.1 202 Accepted
Location: http://localhost:9080/product-catalogs/resources/books/15
...

Java EE 8 and Angular
```

