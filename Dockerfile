FROM openjdk:17-jdk-slim
ADD /target/SkiShop-1.0-SNAPSHOT.jar skishop.jar
ENTRYPOINT ["java","-jar","skishop.jar"]