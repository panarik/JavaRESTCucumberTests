package steps;

import controller.Controller;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseOptions;

import java.util.HashMap;
import java.util.Map;

public class PostUserSteps {

    private final Controller controller = new Controller();
    private ResponseOptions<Response> response; // response for checking

    @Then("Search user by id and check his fields: {string}, {string}, {string}, {string}")
    public void searchUserByIdAndCheckHisFields(String arg0, String arg1, String arg2, String arg3) {

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
        ResponseBody body = response.getBody();
        //ToDo:
    }

}
