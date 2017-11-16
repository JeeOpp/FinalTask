package controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DNAPC on 16.11.2017.
 */
public interface ControllerCommand {
    void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
