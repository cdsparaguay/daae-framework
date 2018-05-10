#!/usr/bin/env bash
sudo rm -rf target
sudo rm app.jar
mvn clean package
cp target/*.jar /app/app.jar
java -jar app.jar