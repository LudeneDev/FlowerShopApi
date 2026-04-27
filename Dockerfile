FROM eclipse-temurin:25-jre-alpine

WORKDIR /app
COPY build/libs/ flowershop.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]