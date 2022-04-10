package steps;

import controller.Controller;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.util.*;

import static org.apache.http.util.Asserts.notNull;

public class UserSteps {

    private final Controller controller = new Controller();
    private ResponseOptions<Response> response; // response for checking
    private List<LinkedHashMap<String, Object>> usersList = new LinkedList<>(); // list with users
    Map<String, String> createdUser = new HashMap<>(); // list with created user fields
    Map<String, String> searchableUser = new HashMap<>(); // list with searched user fields

    @Given("GET users")
    public void getUsers() {
        response = controller.performGET(); // run request
        usersList = response.getBody().jsonPath().get("data"); // fill list with users
    }

    @Then("Users quantity should be {int}")
    public void usersQuantityShouldBe(int quantity) {
        Assertions.assertEquals(usersList.size(), quantity);
    }

    @Then("User name should be {string}")
    public void userNameIs(String expectedName) {
        searchableUser = getStringMap(response.getBody().jsonPath().get("data")); // get current user data

        // parsing response example (all fields current user):
        for (Map.Entry<String, String> entry : searchableUser.entrySet()) {
            System.out.println("key: " + entry.getKey() + " value:" + entry.getValue().toString());
        }

        // Matcher
        Assertions.assertEquals(searchableUser.get("name"), expectedName);
    }

    @Then("User number {int} shouldn't exist")
    public void userNumberDoesNotExist(int number) {
        Assert.assertTrue("User number " + number + " is exist but shouldn't.", isOutOfBounds(usersList, number - 1));
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
        notNull(usersList.get(number - 1).get("name"), "User №" + number + " not exist!");
    }

    @Then("User number {int} should exist")
    public void userNumberShouldExist(int number) {
        Assert.assertFalse("User number " + number + " does not exist.", isOutOfBounds(usersList, number - 1));
    }

    @Then("Status code should be {int}")
    public void serverReturnedCode(int expectedCode) {
        int actualCode = response.getStatusCode();
        Assert.assertEquals("Status code is: '" + actualCode + "' but should be  '" + expectedCode + "'.", actualCode, expectedCode);
    }

    @Then("Search user by this id and compare his fields with created user")
    public void searchUserByIdAndCheckHisFields() {
        response = controller.performGetUserById(createdUser.get("id")); // search created user
        searchableUser = getStringMap(response.getBody().jsonPath().get("data")); // fill fields searched user
        Assert.assertEquals(searchableUser.get("id"), createdUser.get("id"));
        Assert.assertEquals(searchableUser.get("name"), createdUser.get("name"));
        Assert.assertEquals(searchableUser.get("email"), createdUser.get("email"));
        Assert.assertEquals(searchableUser.get("gender"), createdUser.get("gender"));
        Assert.assertEquals(searchableUser.get("status"), createdUser.get("status)"));
    }

    @Given("User name is {string}, user mail is {string}, user gender is {string}, user status is {string}")
    public void userNameIsUserMailIsUserGenderIsUserStatusIs(String name, String email, String gender, String status) {
        Map<String, String> body = new HashMap<>(); // request parameters
        body.put("name", name);
        body.put("email", email);
        body.put("gender", gender);
        body.put("status", status);
        response = controller.performPOST(body);
        try {
            createdUser = getStringMap(response.getBody().jsonPath().get("data")); // create new user fields.

        } catch (ClassCastException e) {
            Assert.fail("Current user already exist. Please create new user with unique email.");
        }
    }

    @Then("User has created and have id")
    public void userHasCreatedAndHaveId() {
        Assert.assertNotNull(createdUser.get("id"));
    }

    private Map<String, String> getStringMap(Map<Object, Object> map) {
        Map<String, String> stringMap = new HashMap<>();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            stringMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return stringMap;
    }

    @Then("Update current user name with {string}")
    public void updateCurrentUserNameWith(String name) {
        Map<String, String> body = new HashMap<>();
        body.put("name", name); // update user name
        body.put("email", createdUser.get("email"));
        body.put("status", createdUser.get("status"));
        controller.performUPDATE(createdUser.get("id"), body);
    }

    @Then("Current user name should be {string}")
    public void currentUserNameShouldBe(String name) {
        response = controller.performGetUserById(createdUser.get("id")); // search created user
        searchableUser = getStringMap(response.getBody().jsonPath().get("data")); // fill fields searched user
        Assert.assertEquals(searchableUser.get("name"), name);
    }
}
