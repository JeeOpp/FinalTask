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
        commandMap.put("authorization", new Authorization());
        commandMap.put("registration", new Registration());
        commandMap.put("dispatcher", new Dispatcher());
        commandMap.put("localization", new Localization());
        commandMap.put("logOut", new LogOut());
    }
    public ControllerCommand getCommand(String method){
        return commandMap.get(method);
    }
}
