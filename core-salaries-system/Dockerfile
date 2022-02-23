FROM adoptopenjdk/openjdk14
ADD target/salarymanager.jar /home/administrator/requests/salarymanager.jar
WORKDIR /home/administrator/requests
#COPY target/salarymanager.jar salarymanager.jar
CMD ["java","-jar", "salarymanager.jar"]