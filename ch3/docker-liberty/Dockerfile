FROM open-liberty:javaee8-java11

ADD --chown=1001:0 http://central.maven.org/maven2/com/h2database/h2/1.4.199/h2-1.4.199.jar /opt/ol/wlp/lib/

COPY liberty-h2-server.xml /config/configDropins/defaults/
