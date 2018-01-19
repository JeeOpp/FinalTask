package controller.command.impl;

import controller.command.ControllerCommand;
import entity.Client;
import entity.Review;
import entity.Taxi;
import entity.User;
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
    private final static String REDIRECT_HOME = "Controller?method=signManager&action=goHomePage";
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        SwitchConstant switchConstant = SwitchConstant.getConstant(action);
        switch (switchConstant){
            case WRITE_REVIEW:
                writeReview(req,resp);
                break;
        }
    }

    private void writeReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        User user = (User) req.getSession().getAttribute("user");
        String role = user.getRole();
        if (role.equals("client")) {
            Client client = (Client) user;
            String taxiIdString;
            Taxi taxi = null;
            if((taxiIdString = req.getParameter("taxiId"))!=null) {
                taxi = new Taxi(Integer.parseInt(taxiIdString));
            }
            Integer orderId = null;
            String orderIdString;
            if((orderIdString = req.getParameter("orderId"))!=null) {
                orderId = Integer.parseInt(orderIdString);
            }
            String comment = req.getParameter("review");
            Review review = new Review(client, taxi, comment);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            FeedbackService feedbackService = serviceFactory.getFeedbackService();
            feedbackService.setReview(review);

            DispatcherService dispatcherService = serviceFactory.getDispatcherService();
            dispatcherService.moveOrderToArchive(orderId);
            new Dispatcher().getClientOrders(req, resp);
        }else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }
}
