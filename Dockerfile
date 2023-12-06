FROM liberica-openjdk-debian:17.0.9-11
COPY target/*.jar TaskManagementSystem.jar
ENTRYPOINT ["java","-jar","TaskManagementSystem.jar"]
