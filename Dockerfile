FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar
COPY build/resources/main/application.yaml application.yaml
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["./gradlew", "bootRun"]
#ENTRYPOINT ["java","-jar","--spring.config.location=classpath:file:/application.yaml","/app.jar"]