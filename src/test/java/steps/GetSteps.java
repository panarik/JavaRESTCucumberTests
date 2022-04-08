package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

public class GetSteps {

    @Given("GET user name for {string}")
    public void getUserNameFor(String url) {
        given().contentType(ContentType.JSON);
    }

    @And("Give user {string}")
    public void giveUser(String userNumber) {
        when()
                .get("https://gorest.co.in/public/v2/users/"+userNumber)
                .then().body("name", is("Menka Pilla"));
    }

    @Then("User {int} name is {string}")
    public void userNameIs(int arg0, String arg1) {
    }
}
