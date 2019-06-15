FROM payara/micro:5.192

COPY ./target/movie-cloud.war $DEPLOY_DIR
CMD ["--nocluster", "--deploymentDir", "/opt/payara/deployments"]
# CMD ["--nocluster", "--deploymentDir", "/opt/payara/deployments", "--contextroot", "/"]