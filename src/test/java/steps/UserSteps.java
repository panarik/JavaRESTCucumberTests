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
    Map<String, String> createdUserFields = new HashMap<>(); // list with created user fields
    Map<String, String> searchableUserFields = new HashMap<>(); // list with searched user fields

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
        searchableUserFields = getStringMap(response.getBody().jsonPath().get("data")); // get current user data

        // parsing response example (all fields current user):
        for (Map.Entry<String, String> entry : searchableUserFields.entrySet()) {
            System.out.println("key: " + entry.getKey() + " value:" + entry.getValue().toString());
        }

        // Matcher
        Assertions.assertEquals(searchableUserFields.get("name"), expectedName);
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
        response = controller.performGetUserById(createdUserFields.get("id")); // search created user
        searchableUserFields = getStringMap(response.getBody().jsonPath().get("data")); // fill fields searched user
        Assert.assertEquals(searchableUserFields.get("id"), createdUserFields.get("id"));
        Assert.assertEquals(searchableUserFields.get("name"), createdUserFields.get("name"));
        Assert.assertEquals(searchableUserFields.get("email"), createdUserFields.get("email"));
        Assert.assertEquals(searchableUserFields.get("gender"), createdUserFields.get("gender"));
        Assert.assertEquals(searchableUserFields.get("status"), createdUserFields.get("status)"));
    }

    @Given("User name is {string}, user mail is {string}, user gender is {string}, user status is {string}")
    public void userNameIsUserMailIsUserGenderIsUserStatusIs(String name, String mail, String gender, String status) {
        Map<String, String> body = new HashMap<>(); // request parameters
        body.put("name", name);
        body.put("email", mail);
        body.put("gender", gender);
        body.put("status", status);
        response = controller.performPOST(body);
    }

    @Then("User has created and have id")
    public void userHasCreatedAndHaveId() {
        try{
            createdUserFields = getStringMap(response.getBody().jsonPath().get("data"));
        } catch (ClassCastException e) {
            Assert.fail("Current user already exist. Please create new user with unique email.");
        }
        System.out.println("user id: " + createdUserFields.get("id"));
    }

    private Map<String, String> getStringMap(Map<Object, Object> map) {
        Map<String, String> stringMap = new HashMap<>();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            stringMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return stringMap;
    }

}
