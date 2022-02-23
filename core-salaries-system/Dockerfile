FROM adoptopenjdk/openjdk14
ADD target/salarymanager.jar /home/administrator/requests/salarymanager.jar
WORKDIR /home/administrator/requests
#COPY target/salarymanager.jar salarymanager.jar
ENTRYPOINT ["java","-jar", "salarymanager.jar"]