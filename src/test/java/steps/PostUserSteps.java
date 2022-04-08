package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.HashMap;
import java.util.Map;

import static controller.Controller.performPOST;

public class PostUserSteps {

    private final String TOKEN = "a54c40b47cebe37441b2c2467d25eb79b67365ae17685a55fb5ee284d8cd5d83";
    private static final String USERS_PATH = "/public-api/users/";

    @Then("User has created and have id")
    public void userHasCreatedAndHaveId() {

    }

    @Then("Search user by id and check his fields: {string}, {string}, {string}, {string}")
    public void searchUserByIdAndCheckHisFields(String arg0, String arg1, String arg2, String arg3) {

    }

    @Given("User name is {string}, user mail is {string}, user gender is {string}, user status is {string}")
    public void userNameIsUserMailIsUserGenderIsUserStatusIs(String name, String mail, String gender, String status) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer "+TOKEN);

        Map<String, String> body = new HashMap<>(); // request parameters
        body.put("name", name);
        body.put("mail", mail);
        body.put("gender", gender);
        body.put("status", status);
        performPOST(USERS_PATH, headers, body);
    }
}
