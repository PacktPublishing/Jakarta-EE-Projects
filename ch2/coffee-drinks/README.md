# coffee-drinks

Microservice project to view coffees in the system.

# Build and Run

See the `buildAndRun.sh` script


You can check docker logs using `logs.sh` when you run locally.


## Endpoints

Details on curl and other aspects are covered under `ch2`'s [README](../README.md)
- When running using the provided script the Docker container will use a port binding of 8020 to container's 8080 port.
- This makes the service available under localhost:8020


### All coffees
[http://localhost:8020/coffee-drinks/resources/coffees](http://localhost:8020/coffee-drinks/resources/coffees)

### A particular coffee type
[http://localhost:8020/coffee-drinks/resources/coffees/Mocha](http://localhost:8020/coffee-drinks/resources/coffees/Mocha)
