# Сборка WAR (контекст — корень проекта)
FROM maven:3.9-eclipse-temurin-11 AS build
WORKDIR /app

COPY 1/pom.xml .
RUN mvn dependency:go-offline -B -q

COPY 1/src ./src
RUN mvn package -DskipTests -B -q

# Запуск в Tomcat (приложение как ROOT — http://localhost:8080/)
FROM tomcat:9.0-jre11-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/servlets-lab1.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
