package controller.command.impl;

import controller.command.ControllerCommand;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DNAPC on 12.12.2017.
 */
public class Feedback implements ControllerCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("writeReview")) {
            writeReview(req,resp);
        }
    }

    private void writeReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String comment = req.getParameter("review");


        ServiceFactory serviceFactory = ServiceFactory.getInstance();

        System.out.println(comment);
    }
}
