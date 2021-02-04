FROM java:8-jdk-alpine
ADD ./target/auth-service-0.0.1-SNAPSHOT.jar auth-service-0.0.1-SNAPSHOT.jar
EXPOSE 8010
ENTRYPOINT ["java", "-jar", "auth-service-0.0.1-SNAPSHOT.jar"]