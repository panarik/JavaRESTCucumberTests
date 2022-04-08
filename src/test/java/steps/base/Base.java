package steps.base;

import controller.Controller;
import io.cucumber.java.Before;

public class Base {

    @Before
    public void testSetup() {
        new Controller();
    }

}
