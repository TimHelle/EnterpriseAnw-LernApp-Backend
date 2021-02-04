# LernApp-Backend 
The Backend is one of three components for LearnApp-application. 
- [Frontend of the application](https://github.com/TimHelle/EnterpriseAnw-LernApp-Frontend)
- [Admin page as a third component of the application](https://github.com/TimHelle/EnterpriseAnw-LernApp-Adminseite)

### How to run the project:

1. Download the code or *run docker container with application from docker hub (uses mysql docker as input! -> execute step 4 first):
```
$ docker run -d -p 9000:8080 --name=user-rest-api \
  --network=spring-rest-network \
  -e MYSQL_ADDR=docker-mysql \
  olgazharikova/learn_app
```
2. Build the project:
```
$ mvn clean package -DskipTests
```
3. Run docker local:
```
$ docker network create spring-rest-network
```
4. Run MySQL:
```
$ docker run -d -p 3306:3306 --name=docker-mysql \
  --network=spring-rest-network \
  --env="MYSQL_USER=spring-user" \
  --env="MYSQL_ROOT_PASSWORD=root" \
  --env="MYSQL_PASSWORD=secret" \
  --env="MYSQL_DATABASE=test" \
  mysql:8.0
```

