FROM openjdk:8-jdk
MAINTAINER Yohanna Lisnichuk <yohanitalisnichuk@gmail.com>
ENV JAVA_HOME              /usr/lib/jvm/java-8-openjdk-amd64
ENV PATH                   $PATH:$JAVA_HOME/bin
ENV TIME_ZONE              America/Asuncion
ENV SPRING_PROFILES_ACTIVE test

ENV TZ=$TIME_ZONE
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN dpkg-reconfigure -f noninteractive tzdata
RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY deploy.sh deploy.sh

EXPOSE 8080