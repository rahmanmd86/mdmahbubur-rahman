# Home challenge from N26 

# Prerequisites
This project is developed and build using the below:
- Java 1.8
- Maven 3.6.0

# Seeded data

Data hardcoded in TransactionService.java class to test the implementation asked

```
private List<Transaction> transactions = Arrays.asList(
        new Transaction(10L, 1000., "cars"),
        new Transaction(11L, 2000., "cars"),
        new Transaction(12L, 3000., "shopping", 10L)
    );
```

# Running the app
Open ternimal and run

```
mvn clean install
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

