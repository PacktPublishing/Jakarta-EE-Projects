FROM payara/micro:5.192

COPY ./target/spares.war $DEPLOY_DIR

#COPY ./target/spares.war $DEPLOY_DIR/ROOT.war

CMD ["--deploymentDir", "/opt/payara/deployments", "--contextroot", "/"]