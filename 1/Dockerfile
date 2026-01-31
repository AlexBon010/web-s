FROM maven:3.9-eclipse-temurin-11 AS build
WORKDIR /app

COPY 1/pom.xml .
RUN mvn dependency:go-offline -B -q

COPY 1/src ./src
RUN mvn package -DskipTests -B -q

FROM tomcat:9.0-jre11-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /build/1/servlets-lab1.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
