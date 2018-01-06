package controller.command;

import controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DNAPC on 16.11.2017.
 */
public class ControllerDirector {
    private Map<String, ControllerCommand> commandMap = new HashMap<>();

    public ControllerDirector(){
        commandMap.put("signManager", new SignManager());
        commandMap.put("dispatcher", new Dispatcher());
        commandMap.put("localization", new Localization());
        commandMap.put("feedback", new Feedback());
        commandMap.put("userManager", new UserManager());
        commandMap.put("taxis", new Taxis());
    }
    public ControllerCommand getCommand(String method){
        return commandMap.get(method);
    }
}
