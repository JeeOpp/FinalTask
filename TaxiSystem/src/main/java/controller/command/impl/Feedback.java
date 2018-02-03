package controller.command.impl;

import static support.constants.OrderConstants.ARCHIVE;
import static support.constants.OrderConstants.ORDER_ID;
import static support.constants.PaginationConstants.*;
import static support.constants.ReviewConstants.REVIEW;
import static support.constants.ReviewConstants.REVIEW_ID;
import static support.constants.UserConstants.*;

import controller.command.ControllerCommand;
import entity.*;
import service.DispatcherService;
import service.FeedbackService;
import service.PaginationService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Responds to requests related to orders
 * Delegates business logic to the {@link FeedbackService}
 * The class implements {@link ControllerCommand}. Responds to request related to reviews.
 */
public class Feedback implements ControllerCommand {
    private final static String REDIRECT_HOME = "Controller?method=signManager&action=goHomePage";
    private final static String REQ_CLIENT_ORDER = "Controller?method=dispatcher&action=getClientOrders";
    private final static String REQ_ALL_REVIEW = "Controller?method=feedback&action=getAllReviews";
    private final static String ADMIN_REVIEW_PAGE = "WEB-INF/Admin/reviews.jsp";
    private final static int INVALID_VALUE = -1;
    private final static int DEFAULT_PAGE = 1;

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
            case GET_ALL_REVIEW:
                getAllReviews(req, resp);
                break;
            case DELETE_REVIEW:
                deleteReview(req,resp);
                break;
            case NONE:
                resp.sendRedirect(REDIRECT_HOME);
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
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(CLIENT)) {
            Client client = (Client) user;

            String taxiIdString = req.getParameter(TAXI_ID);
            Taxi taxi = (taxiIdString != null) ? (Taxi) new Taxi.TaxiBuilder().setId(Integer.parseInt(taxiIdString)).build() : null;

            String orderIdString = req.getParameter(ORDER_ID);
            int orderId = (orderIdString != null) ? Integer.parseInt(orderIdString) : INVALID_VALUE;

            String comment = req.getParameter(REVIEW);
            Review review = new Review(client, taxi, comment);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            FeedbackService feedbackService = serviceFactory.getFeedbackService();
            feedbackService.setReview(review);

            DispatcherService dispatcherService = serviceFactory.getDispatcherService();
            dispatcherService.changeOrderStatus(ARCHIVE, orderId);
            resp.sendRedirect(REQ_CLIENT_ORDER);
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * Use to get all reviews information from all users. Method could been called by admin.
     * As well build a pagination and sent it on admin's reviews page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void getAllReviews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(ADMIN)) {
            String numPage = req.getParameter(NUM_PAGE);
            int page = (numPage != null) ? Integer.parseInt(numPage) : DEFAULT_PAGE;

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            FeedbackService feedbackService = serviceFactory.getFeedbackService();
            List<Review> reviewList = feedbackService.getAllReviewList();


            PaginationService<Review> paginationService = serviceFactory.getPaginationService();
            paginationService.buildPagination(reviewList);
            List<Review> pageReviewList = paginationService.getPagination().getPage(page);


            req.setAttribute(PAGE_REVIEW_LIST, pageReviewList);
            req.setAttribute(PAGE_COUNT, paginationService.getPagination().getCountPages());
            req.setAttribute(CURRENT_PAGE, page);

            req.getRequestDispatcher(ADMIN_REVIEW_PAGE).forward(req, resp);
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }
    /**
     * receives an review id. Delete this review from database.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void deleteReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(ADMIN)) {
            String reviewIdString = req.getParameter(REVIEW_ID);
            int reviewId = (reviewIdString != null) ? Integer.parseInt(reviewIdString) : INVALID_VALUE;

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            FeedbackService feedbackService = serviceFactory.getFeedbackService();
            if (feedbackService.deleteReview(reviewId)) {
                resp.sendRedirect(REQ_ALL_REVIEW);
            }
        }else{
            resp.sendRedirect(REDIRECT_HOME);
        }
    }



    private enum FeedbackAction {
        WRITE_REVIEW("writeReview"),
        GET_ALL_REVIEW("getAllReviews"),
        DELETE_REVIEW("deleteReview"),

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
