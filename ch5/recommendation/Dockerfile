FROM payara/micro:5.192

COPY ./target/recommendation.war $DEPLOY_DIR

CMD ["--deploymentDir", "/opt/payara/deployments","--nocluster"]