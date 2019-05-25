
# Getting started

You can build the code for the projects by running the commands from the `ch4` folder.

## Building the services with one command
From the ch4 folder, execute the script file, which is a convinience script, called `build-all.sh`

```sh
./build-all.sh
```

You may need to give execute permission Ex: $ `chmod u+x build-all.sh` and run this script.

## Building and running with Docker

To build the application image using the default `Dockerfile` present in the 'spares' project folder, run below from `ch4` folder. (**Single command**)

```sh
docker-compose build
```

This builds the project image with the image name and port binding as defined in the `docker-compose.yml` file.
The image will be tagged as `prashantpro/spares:1.0`. You can edit the YML file and replace 'prashantpro' with your docker hub user id. This allows you to push images to your own account.

>OR
To build Docker image directly, you can navigate to the spares project folder and run the docker build command.

```sh
cd spares
docker build . -t prashantpro/spares:1.0
```

Note: You should consider replacing "prashantpro" with your docker hub account id. If you intend to use this image to pull and publish to docker hub.

## Command to run the containers
(Note: It will build missing images first time, BUT will not rebuild images next time.)

```sh
docker-compose up
```

To kill the servers just press
`CTRL+C`

The below will rebuild the image and run the containers

```sh
docker-compose up --build
```

>OR
To rebuild and run in background
```sh
docker-compose up --build -d
```

Open Traefik dashboard page by visiting this link

```sh
http://localhost:8000
```

## To remove (and shutdown) the containers
```sh
docker-compose down
```

Once the containers are running, you can open the browser and load both tenant sites to try them out.

```sh
http://acme.localhost/login.xhtml
http://infi.localhost/login.xhtml
```

## User accounts to try login

### Acme user

Valid credentials:

| Username | admin@acme.org |
|----------|----------------|
| Password | secret         |


### Infi user

Valid credentials:

| Username | admin@infi.org |
|----------|----------------|
| Password | secret         |

>Try entering invalid credentials to see validation messages.


## Experimenting with authorization

Access the MySQL Docker container which should be available on localhost:3306.
Login with credentials 'root' and 'secret'.

Add a new user under acme schema:
INSERT INTO `acme`.`user` (`email`, `password`) VALUES ('john@acme.org', '1234');

Visi the acme site and try using these credentials to login:

| Username | john@acme.org  |
|----------|----------------|
| Password | 1234           |

Expected result:
The user if created in database would be allowed to login, but instead of the site's home page, he will be shown a Forbidden page.

To solve this, we can map the newly added user to the role 'ADMIN'.

Assuming that the user john@acme.org has the id of 2, we can update the user_role table:
INSERT INTO `acme`.`user_role` (`user_id`, `role`) VALUES (2, 'ADMIN');

You need to retry (with a new session) and this time you should be able to view the site page.