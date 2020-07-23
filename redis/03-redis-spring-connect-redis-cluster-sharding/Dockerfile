FROM openjdk:11.0.8-jdk	
EXPOSE 8080
ARG JAR_FILE
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java" ,"-Djava.security.egd=file:/dev/./urandom --spring.config.location=classpath:,file:/app/config/","-jar","app.jar"]

#VOLUME /tmp

# docker build -t spring-redis:latest .