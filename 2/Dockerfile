FROM maven:3.9-eclipse-temurin-11 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B -q

COPY src ./src
RUN mvn package -DskipTests -B -q

FROM tomcat:9.0-jre11-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/hibernate-lab2.war /usr/local/tomcat/webapps/ROOT.war

ENV DB_URL=jdbc:postgresql://db:5432/lab2
ENV DB_USER=lab2
ENV DB_PASSWORD=lab2

EXPOSE 8080
CMD ["catalina.sh", "run"]
