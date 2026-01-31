FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY 3/pom.xml .
RUN mvn dependency:go-offline -B -q

COPY 3/src ./src
RUN mvn package -DskipTests -B -q

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /build/3/rest-lab3-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
