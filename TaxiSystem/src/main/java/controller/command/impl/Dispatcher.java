package controller.command.impl;

import controller.command.ControllerCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Created by DNAPC on 29.11.2017.
 */
public class Dispatcher implements ControllerCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Object position = req.getParameter("position");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(position.toString());
    }
}
