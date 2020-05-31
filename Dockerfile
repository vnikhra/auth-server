FROM openjdk:12-alpine

COPY ./build/libs/AuthServer-0.0.1-SNAPSHOT.jar /usr/app

WORKDIR /usr/app

RUN sh -c 'touch AuthServer-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java", "-jar", "AuthServer-0.0.1-SNAPSHOT.jar"]