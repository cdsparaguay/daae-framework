FROM openjdk:8-jdk
MAINTAINER Yohanna Lisnichuk <yohanitalisnichuk@gmail.com>
ENV JAVA_HOME              /usr/lib/jvm/java-8-openjdk-amd64
ENV PATH                   $PATH:$JAVA_HOME/bin
ENV TIME_ZONE              America/Asuncion
ENV SPRING_PROFILES_ACTIVE test
ENV SPARK_HOME /usr/local/spark

ENV TZ=$TIME_ZONE
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN dpkg-reconfigure -f noninteractive tzdata
RUN apt-get update && apt-get install -y maven

RUN mkdir $SPARK_HOME

RUN wget http://www-eu.apache.org/dist/spark/spark-2.3.1/spark-2.3.1-bin-hadoop2.7.tgz

RUN tar xvf spark-2.3.1-bin-hadoop2.7.tgz -C $SPARK_HOME --strip-components=1

RUN rm -f spark-2.3.1-bin-hadoop2.7.tgz

WORKDIR /app

COPY deploy.sh deploy.sh

EXPOSE 8080