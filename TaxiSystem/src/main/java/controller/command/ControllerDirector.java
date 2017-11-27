package controller.command;

import controller.command.impl.ClientAuthorization;
import controller.command.impl.ClientRegistration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DNAPC on 16.11.2017.
 */
public class ControllerDirector {
    private Map<String, ControllerCommand> commandMap = new HashMap<>();

    public ControllerDirector(){
        commandMap.put("ClientAuthorization", new ClientAuthorization());
        //commandMap.put("TaxiAuthorization", new TaxiAuthorization());
        commandMap.put("ClientRegistration", new ClientRegistration());
    }
    public ControllerCommand getCommand(String method){
        return commandMap.get(method);
    }
}
