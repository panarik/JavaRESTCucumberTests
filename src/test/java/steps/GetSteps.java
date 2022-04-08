package steps;

import controller.Controller;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.junit.jupiter.api.Assertions;

public class GetSteps {

    public static ResponseOptions<Response> response;
    private StringBuilder path = new StringBuilder(); // path to http request

    @Given("GET user name for {string}")
    public void getUserNameFor(String url) {
        path.append(url); // add url to path
    }

    @And("Give user {string}")
    public void giveUser(String userNumber) {
        path.append(userNumber); // add userNumber to path
        response = Controller.getResponseURL(path.toString()); // run request
    }

    @Then("User name is {string}")
    public void userNameIs(String expectedName) {

        // parsing response example:
        System.out.println(response.getBody().print()); // body
        String actualName = response.getBody().jsonPath().get("name"); // value

        // Matcher
        Assertions.assertEquals(actualName, expectedName);
    }

}
