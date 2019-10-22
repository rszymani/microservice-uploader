FROM openjdk:8-jre-alpine
ADD target/fileloader-0.0.1-SNAPSHOT.jar fileloader-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "fileloader-0.0.1-SNAPSHOT.jar"]