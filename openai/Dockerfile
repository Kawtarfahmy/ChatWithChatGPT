FROM openjdk:17-alpine
WORKDIR /app
COPY target/openai-1.0.0.jar openai-1.0.0.jar
COPY ./data-storage /app/data-storage
EXPOSE 8081
ENTRYPOINT ["java","-jar","openai-1.0.0.jar"]