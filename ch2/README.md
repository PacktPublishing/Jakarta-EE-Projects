
## Command to build the images coffee-drinks and coffee-orders
`docker-compose build`

## Command to run the containers
(Note: It will build missing images first time, BUT will not rebuild images next time.)

`docker-compose up`

To kill the servers just press
CTRL+C

The below will rebuild the images and run the containers

`docker-compose up --build`

OR

To rebuild and run in background<br/>
`docker-compose up --build -d`


## To remove (and shutdown) the containers
`docker-compose down`