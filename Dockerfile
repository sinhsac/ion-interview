FROM trinhbiendich/oracle-jdk8
FROM store/oracle/jdk:11
# FROM openjdk:8-jdk-alpine
ARG JAR_FILE=api/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


# docker build -t tnt/server-docker .
# docker run -p 8081:8084 tnt/server-docker
# docker run -p 8082:8686 tnt/server-docker

# docker container ls
# docker stop CONTAINER_ID
# docker rm CONTAINER_ID

# docker images
# docker rmi IMAGE_ID
