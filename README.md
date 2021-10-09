# Spring Boot Foodie App Backend
## Part of a full stack project featuring react app in a separate repo

Foodie App Backend repository contains the source code and documentation of the Spring Boot backend

##### Project Structure:

Project is build based on MVC (Model View Controller) architecture and uses 3 Tier Architecture.

Read more:

https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller

https://en.wikipedia.org/wiki/Multitier_architecture

# Future Plans and Improvements
* Move it to Microservice architecture with Spring Cloud
* Implement CI/CD
* Integration with AWS S3 for image upload
* Deploy it on AWS in a managed Kubernetes cluster as set of microservices

# Clone repository
#### SSH
    git@github.com:akmal2409/spring-foodie-app-backend.git
#### HTTPS
    https://github.com/akmal2409/spring-foodie-app-backend.git
#### Github CLI
    gh repo clone akmal2409/spring-foodie-app-backend

# One touch set up (no need to build and run)
    docker compose up -d

# Manual deployment
### Building project
    mvn clean install 

### Running the project
    mvn spring-boot:run

# Building docker image
    mvn clean package dockerfile:build

# Technologies 
* Spring Framework 5
* Spring Boot 2.5.5
* Hibernate
* Project Lombok
* Okta (OIDC)
* MYSQL
* Docker
* Mapstruct (To map entities to Data Transfer Objects)
* Spotify Maven Dockerfile Plugin

## Prerequisites
* Java 17
* Maven


## Installing required files to build and run the project:
#### Java 17
    https://adoptium.net/
#### Maven
    https://maven.apache.org/download.cgi

