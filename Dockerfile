FROM 361494667617.dkr.ecr.us-east-1.amazonaws.com/amazoncorretto:8
VOLUME /tmp
EXPOSE 8080
ADD target/*.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]