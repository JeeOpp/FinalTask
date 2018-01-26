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


/**
 * Responds to requests related to orders
 * Delegates business logic to the {@link FeedbackService}
 * The class implements {@link ControllerCommand}. Responds to request related to reviews.
 */
public class Feedback implements ControllerCommand {
    private final static String REDIRECT_HOME = "Controller?method=signManager&action=goHomePage";
    private final static int INVALID_ORDER = -1;

    /**
     * Realization of command pattern. Read a action parameter from request and execute special command depending on read parameter.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ACTION);
        FeedbackAction feedbackAction = FeedbackAction.getConstant(action);
        switch (feedbackAction) {
            case WRITE_REVIEW:
                writeReview(req, resp);
                break;
        }
    }

    /**
     * receives a review about order and write it in database. Method could been called by client.
     * If it's success redirect client to client's order page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void writeReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(UserEnum.USER.getValue());
        if (user.getRole().equals(UserEnum.CLIENT.getValue())) {
            Client client = (Client) user;

            String taxiIdString = req.getParameter(UserEnum.TAXI_ID.getValue());
            Taxi taxi = (taxiIdString != null) ? new Taxi(Integer.parseInt(taxiIdString)) : null;

            String orderIdString = req.getParameter(OrderEnum.ORDER_ID.getValue());
            int orderId = (orderIdString != null) ? Integer.parseInt(orderIdString) : INVALID_ORDER;

            String comment = req.getParameter(ReviewEnum.REVIEW.getValue());
            Review review = new Review(client, taxi, comment);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            FeedbackService feedbackService = serviceFactory.getFeedbackService();
            feedbackService.setReview(review);

            DispatcherService dispatcherService = serviceFactory.getDispatcherService();
            dispatcherService.changeOrderStatus(ARCHIVE.getValue(), orderId);
            resp.sendRedirect("Controller?method=dispatcher&action=getClientOrders");
            //new Dispatcher().getClientOrders(req, resp);
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    private enum FeedbackAction {
        WRITE_REVIEW("writeReview"),

        NONE("none");
        private String value;

        FeedbackAction(String value) {
            this.value = value;
        }

        /**
         * return stored in the enum value .
         *
         * @return value.
         */
        public String getValue() {
            return value;
        }

        /**
         * In the dependence on the received value, return special enum.
         *
         * @param action Special enum we are want to get.
         * @return get special enum in accordance with the action value.
         * If there are no matches return a NONE enum.
         */
        public static FeedbackAction getConstant(String action) {
            for (FeedbackAction each : FeedbackAction.values()) {
                if (each.getValue().equals(action)) {
                    return each;
                }
            }
            return NONE;
        }
    }
}
