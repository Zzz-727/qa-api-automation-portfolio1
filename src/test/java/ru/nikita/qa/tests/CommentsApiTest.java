package ru.nikita.qa.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static ru.nikita.qa.config.ApiConfig.requestSpec;

@Tag("api")
@DisplayName("Comments API")
class CommentsApiTest {

    @Test
    @DisplayName("GET /comments?postId=1: фильтрация комментариев по посту")
    void getCommentsByPostId_returnsMatchingComments() {
        given()
                .spec(requestSpec())
                .queryParam("postId", 1)
        .when()
                .get("/comments")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("postId", everyItem(equalTo(1)))
                .body("email", everyItem(matchesRegex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")));
    }
}
