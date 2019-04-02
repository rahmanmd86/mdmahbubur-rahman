# Home challenge from N26 

# Prerequisites
This project is developed and build using the below:
- Java 1.8
- Maven 3.6.0

# Seeded data

Data hardcoded in TransactionService.java class to test the implementation asked

```
private List<Transaction> transactions = Arrays.asList(
        new Transaction(9L, 0., "cars"),
        new Transaction(10L, 0., "cars"),
        new Transaction(11L, 0., "cars"),
    );
```

# Folder structure
```
mdmahbubur-rahman
    |-- with-java-springboot
        |-- src
            |-- main
                |-- java
                    |-- app
                        |-- Application.java
                        |-- Transaction.java
                        |-- TransactionService.java
                        |-- TransactionServiceController.java
            |-- test
                |-- java
                    |-- app
                        |-- ApplicationTest.java
                        |-- SmokeTest.java
```

# Running the app
- Open terminal
- Clone the repo
- Navigate to `with-java-springboot`
- Run

```
mvn clean install -DskipTests
mvn spring-boot:run
```

# Using the service

The services are scoped within and can be used as specified in the spec [SoftwareEngineerInTest_HomeChallenge](https://github.com/rahmanmd86/mdmahbubur-rahman/blob/master/SoftwareEngineerInTest_HomeChallenge.pdf)
 with some exceptions such as using `parentID` as key instead of `parent_id` on the payload of the `PUT` request. Also the `transactionID` key replaces `trancation_id`.

# Running the test

Open terminal and run

```
mvn test
```

# Reference used

[Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)

# Author
## Md Mahbubur Rahman

