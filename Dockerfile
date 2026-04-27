FROM eclipse-temurin:25-jre-alpine

WORKDIR /app
COPY build/libs/ .

ENTRYPOINT ["java", "-jar", "flowershop.jar"]