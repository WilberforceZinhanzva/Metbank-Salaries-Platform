FROM adoptopenjdk/openjdk14
COPY target/salarymanager.jar salarymanager.jar
VOLUME ./uploads/files
ENTRYPOINT ["java","-jar", "salarymanager.jar"]