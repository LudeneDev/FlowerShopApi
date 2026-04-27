FROM gradle:jdk25-corretto as build

WORKDIR /app

COPY . .
run gradle build

FROM eclipse-temurin:25-jre-alpine




WORKDIR /app
COPY build/libs/ .
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "flowershop.jar"]