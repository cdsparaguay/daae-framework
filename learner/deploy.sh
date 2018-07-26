#!/usr/bin/env bash
rm -rf target
rm app.jar
mvn clean package
cp target/*.jar /app/app.jar
java -jar app.jar