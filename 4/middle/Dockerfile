# Контекст — корень проекта
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY 4/middle/pom.xml .
RUN mvn dependency:go-offline -B -q

COPY 4/middle/src ./src
RUN mvn package -DskipTests -B -q

FROM eclipse-temurin:17-jre
WORKDIR /app
# pom.xml задаёт <directory>../../build/4-middle</directory> — JAR не в target/
COPY --from=build /build/4-middle/buy-middle-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
