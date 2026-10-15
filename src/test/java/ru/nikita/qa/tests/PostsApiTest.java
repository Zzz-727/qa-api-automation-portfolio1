package ru.nikita.qa.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static ru.nikita.qa.config.ApiConfig.requestSpec;
import static ru.nikita.qa.specs.ApiSpecs.successfulResponse;

@Tag("api")
@DisplayName("Posts API")
class PostsApiTest {

    @Test
    @DisplayName("GET /posts/1: возвращает пост с корректными обязательными полями")
    void getPostById_returnsExpectedFields() {
        given()
                .spec(requestSpec())
                .pathParam("id", 1)
        .when()
                .get("/posts/{id}")
        .then()
                .spec(successfulResponse())
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("userId", equalTo(1))
                .body("title", not(emptyOrNullString()))
                .body("body", not(emptyOrNullString()));
    }

    @Test
    @DisplayName("GET /posts?userId=1: каждый пост принадлежит указанному пользователю")
    void getPostsByUserId_returnsOnlyMatchingPosts() {
        given()
                .spec(requestSpec())
                .queryParam("userId", 1)
        .when()
                .get("/posts")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(1)));
    }

    @Test
    @DisplayName("GET /posts/1: ответ соответствует JSON Schema")
    void getPost_matchesJsonSchema() {
        given()
                .spec(requestSpec())
                .pathParam("id", 1)
        .when()
                .get("/posts/{id}")
        .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/post-schema.json"));
    }

    @Test
    @DisplayName("POST /posts: возвращает 201 и данные созданного ресурса")
    void createPost_returnsCreatedPayload() {
        String payload = """
                {
                  "title": "API automation portfolio",
                  "body": "Created by Rest Assured and JUnit 5",
                  "userId": 1
                }
                """;

        given()
                .spec(requestSpec())
                .body(payload)
        .when()
                .post("/posts")
        .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", notNullValue())
                .body("title", equalTo("API automation portfolio"))
                .body("body", equalTo("Created by Rest Assured and JUnit 5"))
                .body("userId", equalTo(1));
    }

    @Test
    @DisplayName("PUT /posts/1: возвращает обновлённое представление ресурса")
    void replacePost_returnsUpdatedPayload() {
        String payload = """
                {
                  "id": 1,
                  "title": "Updated title",
                  "body": "Updated body",
                  "userId": 1
                }
                """;

        given()
                .spec(requestSpec())
                .pathParam("id", 1)
                .body(payload)
        .when()
                .put("/posts/{id}")
        .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", equalTo("Updated title"))
                .body("body", equalTo("Updated body"));
    }

    @Test
    @DisplayName("PATCH /posts/1: возвращает изменённое поле")
    void patchPost_returnsChangedField() {
        given()
                .spec(requestSpec())
                .pathParam("id", 1)
                .body("{\"title\":\"Patched title\"}")
        .when()
                .patch("/posts/{id}")
        .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", equalTo("Patched title"));
    }

    @Test
    @DisplayName("DELETE /posts/1: endpoint отвечает успешным статусом")
    void deletePost_returnsSuccessStatus() {
        given()
                .spec(requestSpec())
                .pathParam("id", 1)
        .when()
                .delete("/posts/{id}")
        .then()
                .statusCode(anyOf(is(200), is(204)));
    }
}
