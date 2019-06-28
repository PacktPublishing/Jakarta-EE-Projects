# FROM payara/micro:5.192

# COPY ./target/recommendation.war $DEPLOY_DIR

# CMD ["--deploymentDir", "/opt/payara/deployments","--nocluster"]

FROM open-liberty:javaee8-java11

COPY liberty/server.xml /config/

COPY ./target/recommendation.war /config/dropins/
