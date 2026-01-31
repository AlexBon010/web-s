# Контекст — корень проекта
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY 4/junior/pom.xml .
RUN mvn dependency:go-offline -B -q

COPY 4/junior/src ./src
RUN mvn package -DskipTests -B -q

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/weather-junior-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
