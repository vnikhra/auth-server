FROM openjdk:12-alpine

RUN mkdir /usr/src

COPY . /usr/src

WORKDIR /usr/src

RUN apk add --no-cache gradle

RUN gradle clean bootJar

RUN mkdir -p /usr/app

RUN cp ./build/libs/AuthServer-0.0.1-SNAPSHOT.jar /usr/app

WORKDIR /usr/app

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.5.0/wait /wait
RUN chmod +x /wait

RUN sh -c 'touch AuthServer-0.0.1-SNAPSHOT.jar'

CMD /wait

ENTRYPOINT ["java", "-jar", "AuthServer-0.0.1-SNAPSHOT.jar"]