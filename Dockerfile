FROM openjdk:17-jdk-slim
RUN mvn clean package -DskipTests
ARG JAR_FILE=target/Prog3-Mutantes-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]