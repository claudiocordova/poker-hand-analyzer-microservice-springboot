FROM 361494667617.dkr.ecr.us-west-2.amazonaws.com/amazoncorretto:17
VOLUME /tmp
EXPOSE 8080
ADD target/*.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]