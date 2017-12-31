package controller.command.impl;

import controller.command.ControllerCommand;
import entity.Client;
import entity.Review;
import entity.Taxi;
import service.DispatcherService;
import service.FeedbackService;
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
        Client client = (Client) req.getSession().getAttribute("user");
        Taxi taxi = new Taxi(Integer.parseInt(req.getParameter("taxiId")));
        String comment = req.getParameter("review");

        Review review = new Review(client,taxi,comment);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FeedbackService feedbackService = serviceFactory.getFeedbackService();
        feedbackService.setReview(review);
        new Dispatcher().getClientOrders(req, resp);
    }
}
