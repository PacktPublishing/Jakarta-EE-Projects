FROM prashantpro/open-liberty:19

COPY liberty/server.xml /config/

COPY ./target/product-ads.war /config/dropins/

# Uncomment below to run server in debug mode.
# It will wait for process/IDE to attach debugger
#CMD ["/opt/ol/wlp/bin/server", "debug", "defaultServer"]