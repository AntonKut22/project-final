FROM openjdk:17-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} jira-1.0.jar

ENTRYPOINT ["java", "-jar", "/jira-1.0.jar"]
