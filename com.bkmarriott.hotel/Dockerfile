FROM eclipse-temurin:17-alpine
COPY ./build/libs/hotel-0.0.1.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]