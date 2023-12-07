FROM bellsoft/liberica-openjdk-alpine:17
COPY target/*.jar tms.jar
ENTRYPOINT ["java","-jar","tms.jar"]
