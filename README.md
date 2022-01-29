[![Build Status](https://gitlab.com/akmal2409/spring-foodie-app-backend/badges/main/pipeline.svg)](https://gitlab.com/akmal2409/spring-foodie-app-backend/)
# Spring Boot Foodie App Backend
## Part of a full stack project featuring react app in a separate repo
https://github.com/akmal2409/spring-foodie-app-react

Foodie App Backend repository contains the source code and documentation of the Spring Boot backend

##### Project Structure:

Project is build based on MVC (Model View Controller) architecture and uses 3 Tier Architecture.

Read more:

https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller

https://en.wikipedia.org/wiki/Multitier_architecture

# Future Plans and Improvements
* Move it to Microservice architecture with Spring Cloud
* ~~Implement CI~~/CD
* Implemented ~~Integration with AWS S3 for image upload~~
* Deploy it on AWS in a managed Kubernetes cluster as set of microservices
* ~~Add ElasticSearch
* Integrate with TomTom API to query delivery places

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
    You have to set following environmental properties on your machine using following commands
    (Windows CLI) $ set PROPERTY_NAME=VALUE
    (Linux CLI) $ export PROPERTY_NAME=VALUE
    
    OKTA_OAUTH2_ISSUER=https://issuer.domain
    OKTA_OAUTH2_CLIENT_ID=<clientId>
    OKTA_OAUTH2_CLIENT_SECRET=<clientSecret>
    CLOUD_AWS_CREDENTIALS_ACCESS_KEY=<aws access key>
    CLOUD_AWS_CREDENTIALS_SECRET_KEY=<aws secret key>

    

### Building project
    mvn clean install 

### Running the project
    mvn spring-boot:run

# Building docker image
    mvn clean package dockerfile:build
    
# Running unit tests
    mvn clean test

# Running integration tests with unit tests
    mvn clean verify -P integration-test

# Technologies 
* Spring Framework 5
* Spring Boot 2.5.5
* Hibernate
* Project Lombok
* Okta (OIDC)
* AWS S3 Image Upload
* MYSQL
* Docker
* Mapstruct (To map entities to Data Transfer Objects)
* Spotify Maven Dockerfile Plugin
* Mockito
* JUnit 5
* Testcontainers
* Maven Surefire for Unit Tests
* Maven Failsafe for integration tests

## Prerequisites
* Java 17
* Maven


## Installing required files to build and run the project:
#### Java 17
    https://adoptium.net/
#### Maven
    https://maven.apache.org/download.cgi

