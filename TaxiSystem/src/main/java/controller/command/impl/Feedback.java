package controller.command.impl;

import static entity.entityEnum.OrderEnum.OrderAction.*;
import controller.command.ControllerCommand;
import entity.Client;
import entity.Review;
import entity.Taxi;
import entity.User;
import entity.entityEnum.OrderEnum;
import entity.entityEnum.ReviewEnum;
import entity.entityEnum.UserEnum;
import service.DispatcherService;
import service.FeedbackService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Feedback implements ControllerCommand {
    private final static String REDIRECT_HOME = "Controller?method=signManager&action=goHomePage";
    private final static int INVALID_ORDER = -1;
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ACTION);
        FeedbackAction feedbackAction = FeedbackAction.getConstant(action);
        switch (feedbackAction){
            case WRITE_REVIEW:
                writeReview(req,resp);
                break;
        }
    }

    private void writeReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        User user = (User) req.getSession().getAttribute(UserEnum.USER.getValue());
        if (user.getRole().equals(UserEnum.CLIENT.getValue())) {
            Client client = (Client) user;

            String taxiIdString = req.getParameter(UserEnum.TAXI_ID.getValue());
            Taxi taxi = (taxiIdString != null)? new Taxi(Integer.parseInt(taxiIdString)): null;

            String orderIdString = req.getParameter(OrderEnum.ORDER_ID.getValue());
            int orderId = (orderIdString != null)? Integer.parseInt(orderIdString) : INVALID_ORDER;

            String comment = req.getParameter(ReviewEnum.REVIEW.getValue());
            Review review = new Review(client, taxi, comment);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            FeedbackService feedbackService = serviceFactory.getFeedbackService();
            feedbackService.setReview(review);

            DispatcherService dispatcherService = serviceFactory.getDispatcherService();
            dispatcherService.changeOrderStatus(ARCHIVE.getValue(),orderId);
            new Dispatcher().getClientOrders(req, resp);
        }else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }
    private enum FeedbackAction{
        WRITE_REVIEW("writeReview"),

        NONE("none");
        private String value;

        FeedbackAction(String value){
            this.value = value;
        }
        public String getValue(){
            return value;
        }

        public static FeedbackAction getConstant(String action){
            for (FeedbackAction each: FeedbackAction.values()){
                if(each.getValue().equals(action)){
                    return each;
                }
            }
            return NONE;
        }
    }
}
