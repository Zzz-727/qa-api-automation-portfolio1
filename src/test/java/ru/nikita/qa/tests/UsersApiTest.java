package ru.nikita.qa.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static ru.nikita.qa.config.ApiConfig.requestSpec;

@Tag("api")
@DisplayName("Users API")
class UsersApiTest {

    @Test
    @DisplayName("GET /users/1: возвращает пользователя с корректным email")
    void getUserById_returnsValidEmail() {
        given()
                .spec(requestSpec())
                .pathParam("id", 1)
        .when()
                .get("/users/{id}")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("name", not(emptyOrNullString()))
                .body("email", matchesRegex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }

    @Test
    @DisplayName("GET /users: возвращает непустой список пользователей")
    void getUsers_returnsNonEmptyList() {
        given()
                .spec(requestSpec())
        .when()
                .get("/users")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("id", everyItem(notNullValue()))
                .body("name", everyItem(not(emptyOrNullString())));
    }

    @Test
    @DisplayName("GET /users/9999: несуществующий пользователь возвращает 404")
    void getNonExistingUser() {
        given()
                .spec(requestSpec())
                .pathParam("id", 9999)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(404);
    }
}
