package controller.command;

import controller.command.impl.SignManager;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DNAPC on 29.01.2018.
 */
public class ControllerDirectorTest {
    private static final String SIGN_MANAGER_COMMAND = "signManager";

    @Test
    public void getCommand() throws Exception {
        ControllerDirector controllerDirector = new ControllerDirector();
        ControllerCommand controllerCommand = controllerDirector.getCommand(SIGN_MANAGER_COMMAND);
        assertEquals(true, controllerCommand instanceof SignManager);
    }

}