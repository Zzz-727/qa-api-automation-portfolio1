package ru.nikita.qa.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public final class ApiConfig {
    public static final String BASE_URI = System.getProperty(
            "api.baseUri",
            System.getenv().getOrDefault("API_BASE_URI", "https://jsonplaceholder.typicode.com")
    );

    private ApiConfig() {
    }

    public static RequestSpecification requestSpec() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setAccept(JSON)
                .setContentType(JSON)
                .build();
    }
}
