FROM gradle:7.6.2-jdk11-alpine

COPY . .

EXPOSE 8080

RUN gradle build

ENTRYPOINT ["java", "-jar", "build/libs/homebankingAP-0.0.1-SNAPSHOT.jar"]