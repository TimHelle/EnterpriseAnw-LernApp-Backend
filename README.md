# EnterpriseAnw-LernApp-Backend
Das Backend für das LernApp-Projekt für Enterprise Anwendungen

### Table of Contents:  
TODO

### Installation:
TODO

### How to run the project:

Build the project:
```
$ mvn clean package -DskipTests
```
Run docker local:
```
$ docker network create spring-rest-network
```
Run MySQL:
```
docker run -d -p 3306:3306 --name=docker-mysql \
  --network=spring-rest-network \
  --env="MYSQL_USER=spring-user" \
  --env="MYSQL_ROOT_PASSWORD=root" \
  --env="MYSQL_PASSWORD=secret" \
  --env="MYSQL_DATABASE=test" \
  mysql:8.0
```
  
test:
```
$ curl -X GET "http://localhost:8080/api/questions"
```
