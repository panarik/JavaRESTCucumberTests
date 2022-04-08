package steps.base;

import controller.Controller;
import io.cucumber.java.Before;

public class BaseSteps {

    @Before
    public void testSetup() {
        new Controller();
    }

}
