package ru.nikita.qa.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;

public final class ApiSpecs {
    private ApiSpecs() {
    }

    public static ResponseSpecification successfulResponse() {
        return new ResponseSpecBuilder()
                .expectResponseTime(lessThan(5000L))
                .build();
    }
}
