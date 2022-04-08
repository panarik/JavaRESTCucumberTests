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

    public static ResponseOptions<Response> getResponseURL(String url) {
        try {
            return request.get(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getResponseUrlWithParam(String url, Map<String, String> params) {
        request.pathParams(params);
        try {
            request.get(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
