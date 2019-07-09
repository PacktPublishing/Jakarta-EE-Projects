# Build
mvn clean package && docker build -t org.jakartaeeprojects/movie-cloud .

# RUN

docker rm -f movie-cloud || true && docker run -d -p 8080:8080 -p 4848:4848 --name movie-cloud org.jakartaeeprojects/movie-cloud 