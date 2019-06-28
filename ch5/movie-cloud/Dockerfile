# FROM payara/micro:5.192

# COPY ./target/movie-cloud.war $DEPLOY_DIR

# CMD ["--deploymentDir", "/opt/payara/deployments","--nocluster"]

FROM open-liberty:javaee8-java11

COPY liberty/server.xml /config/

COPY ./target/movie-cloud.war /config/dropins/