package controller.command;

import controller.command.impl.Authorization;
import controller.command.impl.Dispatcher;
import controller.command.impl.Localization;
import controller.command.impl.Registration;

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
        commandMap.put("callTaxi", new Dispatcher());
        commandMap.put("localization", new Localization());
    }
    public ControllerCommand getCommand(String method){
        return commandMap.get(method);
    }
}
