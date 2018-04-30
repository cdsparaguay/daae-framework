#!/usr/bin/env bash
mvn clean package
cp target/*.jar /app/app.jar
java -jar app.jar