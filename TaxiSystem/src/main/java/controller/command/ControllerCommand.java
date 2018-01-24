package controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The interface required to the pattern command
 */
public interface ControllerCommand {
    /**
     * String constant, takes the query parameter
     */
    String ACTION = "action";

    /**
     * Needed to implement command pattern
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Throw exception if there are some problem with servlet.
     * @throws IOException Throw IOException id there are some problem in input/output stream.
     */
    void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
