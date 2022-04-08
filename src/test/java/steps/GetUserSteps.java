package steps;

import controller.Controller;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.apache.http.util.Asserts.notNull;

public class GetUserSteps {

    private static ResponseOptions<Response> response;
    private static List<LinkedHashMap<String, Object>> userList = new LinkedList<>(); // list with users
    private static final String USERS_PATH = "/public-api/users/";

    private StringBuilder path = new StringBuilder(USERS_PATH); // path to http request

    @Given("GET users")
    public void getUsers() {
        response = Controller.performGET(path.toString()); // run request
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

    @Then("User number {int} shouldn't exist")
    public void userNumberDoesNotExist(int number) {
        Assert.assertTrue("User number " + number + " is exist but shouldn't.", isOutOfBounds(userList, number - 1));
    }

    private boolean isOutOfBounds(List list, int pos) {
        try {
            list.get(pos);
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
        return false;
    }

    @Then("User number {int} should have a name")
    public void userNumberShouldHaveAName(int number) {
        notNull(userList.get(number - 1).get("name"), "User №" + number + " not exist!");
    }

    @Then("User number {int} should exist")
    public void userNumberShouldExist(int number) {
        Assert.assertFalse("User number " + number + " does not exist.", isOutOfBounds(userList, number - 1));
    }

    @Then("Status code should be {int}")
    public void serverReturnedCode(int expectedCode) {
        int actualCode = response.getStatusCode();
        Assert.assertEquals("Status code is: '" + actualCode + "' but should be  '" + expectedCode + "'.", actualCode, expectedCode);
    }
}
