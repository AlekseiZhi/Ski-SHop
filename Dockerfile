#FROM openjdk:17-jdk-slim
#COPY . ./app
#RUN javac SkiShopApplication.java
#CMD ["java", "SkiShopApplication"]

#FROM openjdk:17-jdk-slim
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM openjdk:17-jdk-slim
ADD /target/SkiShop-1.0-SNAPSHOT.jar skishop.jar
ENTRYPOINT ["java","-jar","skishop.jar"]