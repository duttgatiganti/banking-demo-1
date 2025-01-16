FROM maven:3.8.5-openjdk-17-slim AS build
LABEL namw="axr"
WORKDIR /app

RUN groupadd appuser && useradd -r -g appuser appuser \
    apt update -y && apt install maven -y
USER appuser
COPY src /app
COPY pom.xml /app
RUN mvn clean package

ENV AWS_DEFAULT_REGION=AP-SOUTH-1A


FROM openjdk:11-jre-slim

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

CMD ["java","-jar","app.jar"]
