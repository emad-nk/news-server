## Application

The project is based on a small web service which uses the following technologies:

* Kotlin
* Spring Boot
* Database H2 (In-Memory)
* Maven

## Running the application

For running the application you can use one of these approaches:

Running using `mvn` command:

First run below command to install the application and running all the tests:
```
mvn clean install 

```
Then run below command to start the application:
```
mvn spring-boot:run
```
Another approach for running the application, would be running it through the provider `jar` file under the `jar` folder:
```
java -jar news_publisher-1.0.0-SNAPSHOT.jar
```

## Application flaws

* No authentication/authorization
    * Depending to use case basic-auth or oAuth should be added
* No CI/CD file has been provided
* Currently the application runs on `dev` mode, therefore multiple profiles should be added and the application in production should be running with `environment variable` defining that it's running in production `prod`.
* Since there is not much data to play with, endpoints that `list` articles/authors do not have `paging` so in case of listing millions of rows, it will kill the application.
* For simplicity every commit has been pushed directly to the master instead of feature branch.
