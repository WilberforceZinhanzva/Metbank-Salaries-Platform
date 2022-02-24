FROM adoptopenjdk/openjdk14
WORKDIR /app
ADD target/salarymanager.jar /app
#COPY target/salarymanager.jar salarymanager.jar
ENTRYPOINT ["java","-jar", "/app/salarymanager.jar"]