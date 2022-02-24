FROM adoptopenjdk/openjdk14
WORKDIR /app
COPY target/salarymanager.jar /app
#COPY target/salarymanager.jar salarymanager.jar
ENTRYPOINT ["java","-jar", "salarymanager.jar"]