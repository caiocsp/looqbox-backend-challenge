# Looqbox Backend Challenge

This repository uses a  **Service Layer Pattern** to give a structure that includes:

- Entity: Represents the domain and map objects for the database tables.

- Service: Contains business logic and calls repository methods to access data.

- Controller: Handles HTTP requests and calls service methods to process the data and return the appropriate response.

## This projects alson includes:

- Utils classes to give genereic methods to get more usability and gives more easier access to common tools of the project.
- Tests to ensure safer build.

## Project Requirements

- Java 17+
- Spring Boot Framework
- Gradle for Dependency manegement

## Build instructions

On terminal, use the follow command to build:

~~~cmd
./gradlew clean build
~~~

## Bottleneck Points

- To give a more powerful performance, on a future perspective, using caching is a first-minded update that I choose to implement in this project.

- Using Pojo to give more safer validations and it would make changes more practical and agile for future functional demands.

## Project Structure Diagram

![Project Structure Diagram](/looqBox_project_structure_Model.png)

