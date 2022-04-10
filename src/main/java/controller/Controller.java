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
        return given().spec(setupSpec("")).get();
    }

    public Response performGetUserById(String id) {
        RequestSpecification requestSpec = given().spec(setupSpec(id).headers(getHeaders()));
        return requestSpec.get();
    }

    /**
     * Perform POST request.
     * URL: https://gorest.co.in/public-api/users/
     * Headers: Content-Type, Authorization.
     *
     * @param body - Custom body with {@link HashMap} format: key-value.
     * @return {@link Response} For checking and parsing response fields.
     */
    public Response performPOST(Map<String, String> body) {
        return given().spec(setupSpec("")).headers(getHeaders()).body(body).post(); // POST with Spec options
    }

    public Response performUPDATE(String id, Map<String, String> body) {
        return given().spec(setupSpec(id)).headers(getHeaders()).body(body).patch(); // UPDATE with Spec options
    }

    private RequestSpecification setupSpec(String path) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://gorest.co.in/public-api/users/" + path)
                .build()
                .log().all();
    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer a54c40b47cebe37441b2c2467d25eb79b67365ae17685a55fb5ee284d8cd5d83");
        return headers;
    }

}
