package controller;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Controller {

    /**
     * Perform GET request.
     * URL: https://gorest.co.in/public-api/users/
     * Headers: standard
     * Body: NONE
     *
     * @return {@link Response} For checking and parsing response fields.
     */
    public Response performGET() {
        return given().spec(setupSpec()).get();
    }

    /**
     * Perform POST request.
     * URL: https://gorest.co.in/public-api/users/
     * Headers: Content-Type, Authorization.
     * Body: NONE
     *
     * @param body - Custom body with {@link HashMap} format: key-value.
     * @return {@link Response} For checking and parsing response fields.
     */
    public Response performPOST(Map<String, String> body) {
        return given().spec(setupSpec()).headers(getHeaders()).body(body).post(); // post with Spec options
    }

    private RequestSpecification setupSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://gorest.co.in/public-api/")
                .setBasePath("/users")
                .setContentType(ContentType.JSON)
                .build();
    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer a54c40b47cebe37441b2c2467d25eb79b67365ae17685a55fb5ee284d8cd5d83");
        return headers;
    }

}
