# QA API Automation Portfolio

Automated REST API tests using Java, Rest Assured, JUnit 5, and Maven.

## Project Overview

This project demonstrates API testing skills through automated tests for the JSONPlaceholder REST API.

## Tech Stack

- Java 17
- Maven
- Rest Assured
- JUnit 5
- Git and GitHub
- GitHub Actions

## Test Coverage

The project includes automated tests for:

- **Posts API** — retrieving posts, creating and updating posts, and deleting posts.
- **Users API** — retrieving users and validating response data.
- **Comments API** — retrieving comments and validating response fields.
- **Negative scenarios** — checking responses for non-existent posts and users, and an empty comments list.

## Running Tests Locally

Make sure Java 17 and Maven are installed.

Run all tests from the project root:

```bash
mvn clean test
```

## Continuous Integration

GitHub Actions automatically builds the project and runs the test suite when changes are pushed to the repository.

## Repository

[qa-api-automation-portfolio1](https://github.com/Zzz-727/qa-api-automation-portfolio1)