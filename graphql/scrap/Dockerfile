FROM openjdk:13-jdk-alpine

RUN mkdir /graphql-sample

COPY garget/scrap-0.0.1-SNAPSHOT.jar /graphql-sample/app.jar

WORKDIR /graphql-sample

CMD ["sh", "-c", "java -Dspring.profiles.active=$JAVA_ENV -jar app.jar"]