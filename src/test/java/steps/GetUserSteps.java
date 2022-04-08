package steps;

import controller.Controller;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.junit.jupiter.api.Assertions;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.apache.http.util.Asserts.notNull;

public class GetUserSteps {

    public static ResponseOptions<Response> response;
    private static List<LinkedHashMap<String, Object>> userList = new LinkedList<>(); // list with users

    private StringBuilder path = new StringBuilder(); // path to http request

    @Given("GET users from {string}")
    public void getUsersFrom(String url) {
        path.append(url); // add url to path
        response = Controller.getResponseURL(path.toString()); // run request
        userList = response.getBody().jsonPath().get("data"); // fill list with users
    }

    @Then("Users quantity should be {int}")
    public void usersQuantityShouldBe(int quantity) {
        Assertions.assertEquals(userList.size(), quantity);
    }

    @Then("User name should be {string}")
    public void userNameIs(String expectedName) {
        Map<String, Object> responseData = response.getBody().jsonPath().get("data"); // get current user data

        // parsing response example (all fields current user):
        for (Map.Entry<String, Object> entry : responseData.entrySet()) {
            System.out.println("key: " + entry.getKey() + " value:" + entry.getValue().toString());
        }

        // Matcher
        Assertions.assertEquals(responseData.get("name"), expectedName);
    }

    @Then("User number {int} should have a name")
    public void userNumberShouldHaveAName(int number) {
        notNull(userList.get(number).get("name"), "User №" + number + " not exist!");
    }
}
