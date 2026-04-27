FROM eclipse-temurin:25-jre-alpine




WORKDIR /app
COPY build/libs/flowershop.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080
