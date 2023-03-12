docker run -dit openjdk:8-jdk-alpine
docker container cp target/docker-in-5-steps-todo-rest-api-h2-1.0.0.RELEASE.jar 28d5e5d893fdb1530e9920ff66fee252adc834a8b572b77b3fdf7d316730127c:/tmp
docker container exec romantic_aryabhata ls /tmp
docker container commit romantic_aryabhata in28min/manual-todo-rest-api:v1
docker container commit --change='CMD ["java","-jar","/tmp/docker-in-5-steps-todo-rest-api-h2-1.0.0.RELEASE.jar"]' romantic_aryabhata in28min/manual-todo-rest-api:v2
docker run -d -p 5000:5000 in28min/manual-todo-rest-api:v2

docker login
docket push claudiocordova/pokerhandanalyzerapi:0.0.1-SNAPSHOT