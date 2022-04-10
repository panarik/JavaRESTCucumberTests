package controller;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import util.JsonParser;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Controller {

    JsonParser parser = new JsonParser();

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
        return checkErrors(given().spec(setupSpec("")).headers(getHeaders()).body(body).post()); // POST with Spec options
    }

    public Response performUPDATE(String id, Map<String, String> body) {
        return checkErrors(given().spec(setupSpec(id)).headers(getHeaders()).body(body).patch()); // UPDATE with Spec options
    }

    public Response performDELETE(String id) {
        return checkErrors(given().spec(setupSpec(id)).headers(getHeaders()).delete()); // DELETE with Spec options
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
        headers.put("Authorization", "Bearer " + parser.getToken());
        return headers;
    }

    /**
     * Check Response for errors.
     *
     * @param response {@link Response} this response
     * @return {@link Response} response if not contain any errors.
     */
    private Response checkErrors(Response response) {
        int code = response.body().jsonPath().get("code");
        switch (code) {
            case (401):
                throw new RuntimeException("Authentication failed. Need valid token");
            case (422):
                throw new RuntimeException("This user already been taken.\nResponse body: " + response.body().print());
        }
        return response;
    }
}
