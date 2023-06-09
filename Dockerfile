# Definição de build para a imagem do Spring boot
FROM openjdk:8-jdk-alpine as build

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} api_itau.jar
ENTRYPOINT ["java","-jar","/api_itau.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]
EXPOSE 8080:8080
