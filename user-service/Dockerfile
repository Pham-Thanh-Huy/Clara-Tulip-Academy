FROM openjdk:21-jdk AS Build

WORKDIR /app

COPY src ./src
COPY pom.xml .

RUN ["mvn", "clean", "package", "-Dmaven.skip.test"]

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=Build /app/user-service-0.0.1-SNAPSHOT.jar ./eureka-server-0.0.1-SNAPSHOT.jar
COPY --from=Build /app/src/resources/application-prod.yml ./application-prod.yml

CMD java -jar user-service-0.0.1-SNAPSHOT.jar --spring.config.localtion=file:///app/application-prod.yml --spring.profiles.active=prod
