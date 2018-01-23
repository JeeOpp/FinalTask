package controller.command;

import controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class ControllerDirector {
    private static final String SIGN_MANAGER_COMMAND = "signManager";
    private static final String DISPATCHER_COMMAND = "dispatcher";
    private static final String LOCALIZATION_COMMAND = "localization";
    private static final String FEEDBACK_COMMAND = "feedback";
    private static final String USER_MANAGER_COMMAND = "userManager";
    private static final String TAXIS_COMMAND = "taxis";
    private static final String MAILER_COMMAND = "mailer";
    private final Map<String, ControllerCommand> commandMap = new HashMap<>();

    public ControllerDirector(){
        commandMap.put(SIGN_MANAGER_COMMAND, new SignManager());
        commandMap.put(DISPATCHER_COMMAND, new Dispatcher());
        commandMap.put(LOCALIZATION_COMMAND, new Localization());
        commandMap.put(FEEDBACK_COMMAND, new Feedback());
        commandMap.put(USER_MANAGER_COMMAND, new UserManager());
        commandMap.put(TAXIS_COMMAND, new Taxis());
        commandMap.put(MAILER_COMMAND, new Mailer());
    }
    public ControllerCommand getCommand(String method){
        return commandMap.get(method);
    }
}
