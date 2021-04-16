# API Test Automation Framework

## Overview

The API test automation framework uses the open source project RestAssured - <https://github.com/rest-assured/rest-assured>.

See <https://www.weatherbit.io/api/swaggerui> for details of the APIs provided.

## Configuration and Environments

REST Assured is a Java DSL for simplifying testing of REST based services built on top of HTTP Builder. 
It supports POST, GET, PUT, DELETE, OPTIONS, PATCH and HEAD requests and can be used to validate and verify the response of these requests.

Within the framework the following environment configurations are available.

## Test Runners

Two different test runner strategies are presented:

- Tag based test runner - all feature files are processed, and tags used to determine which test are run.
- API/feature folder based test runner - all feature files within a specific feature file folder are processed.

Choose the test runner strategy which represents the best approach for your customer.

## Data Parameterization

Test Data has been achieved using TestNG annotation DataProviders
@DataProviders

## To Execute and generate reports in TestNG  

Execute testng.xml

## To Execute using Maven

To run the `posts` api tests:

```bash
mvn clean test 
```
