FROM gcr.io/distroless/java:11
ADD ./target/spares-thorntail.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar", "-Djava.net.preferIPv4Stack=true"]