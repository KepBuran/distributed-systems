FROM openjdk:17-alpine

WORKDIR /opt/server
COPY ./target/taskservice-0.0.1-SNAPSHOT.jar server.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "server.jar"]
