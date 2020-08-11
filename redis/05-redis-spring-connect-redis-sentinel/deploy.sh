#!/bin/bash
mvn clean package
docker build --build-arg JAR_FILE=target/*.jar -t netstart/spring-redis:latest .
docker push netstart/spring-redis:latest
kubectl run app-spring-redis --image=netstart/spring-redis:latest
