FROM openjdk:8-alpine
RUN mkdir /app
COPY ./build/libs/wordle-all.jar /app
WORKDIR /app
ENTRYPOINT ["java", "-jar", "wordle-all.jar"]