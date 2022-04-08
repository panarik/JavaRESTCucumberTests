package controller;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class Controller {

    public static RequestSpecification request;

    public Controller() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("http://gorest.co.in:80");
        builder.setContentType(ContentType.JSON);
        request = RestAssured.given().spec(builder.build());
    }

    public static ResponseOptions<Response> performGET(String path) {
        try {
            return request.get(new URI(path));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseOptions<Response> performPOST(String path, Map<String, String> headers, Map<String, String> body) {
        request.headers(headers); // add headers
        request.body(body); // add body
        try {
            URI uri = new URI(path);
            return request.post(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

}
