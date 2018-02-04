package controller.command.impl;

import com.google.gson.Gson;
import controller.command.ControllerCommand;
import entity.*;
import service.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static support.constants.OrderConstants.*;
import static support.constants.PaginationConstants.*;
import static support.constants.UserConstants.*;

/**
 * Responds to requests related to orders
 * Delegates business logic to the {@link DispatcherService}
 * The class implements {@link ControllerCommand}.
 */
public class Dispatcher implements ControllerCommand {
    private final static String REDIRECT_HOME = "Controller?method=signManager&action=goHomePage";
    private final static String CLIENT_ORDER_PAGE = "WEB-INF/Client/orders.jsp";
    private final static String TAXI_ORDER_PAGE = "WEB-INF/Taxi/orders.jsp";
    private final static String ADMIN_ORDER_PAGE = "WEB-INF/Admin/orders.jsp";
    private final static String CALL_TAXI_PAGE = "WEB-INF/Client/callTaxi.jsp";
    private final static String REQ_CLIENT_ORDER = "Controller?method=dispatcher&action=getClientOrders";
    private final static String REQ_TAXI_ORDER = "Controller?method=dispatcher&action=getTaxiOrders";
    private final static String REQ_PRE_ORDER = "Controller?method=dispatcher&action=preOrder";
    private final static String REQ_ALL_ORDER = "Controller?method=dispatcher&action=getAllOrders";
    private final static String AVAILABLE_TAXI = "availableTaxiList";
    private final static String ZERO_COUNT = "0";
    private final static int INVALID_ORDER = -1;
    private final static int DEFAULT_PAGE = 1;
    private final static String JSON_CONTENT = "application/json";

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
        DispatcherAction dispatcherAction = DispatcherAction.getConstant(action);

        switch (dispatcherAction) {
            case ACCEPT_ORDER:
                acceptOrder(req, resp);
                break;
            case CALL_TAXI:
                callTaxi(req, resp);
                break;
            case CANCEL_ORDER:
                cancelOrder(req, resp);
                break;
            case DELETE_ORDER:
                deleteOrder(req,resp);
                break;
            case DELETE_OBSOLETE_ORDERS:
                deleteObsoleteOrders(req, resp);
                break;
            case GET_ALL_ORDERS:
                getAllOrders(req, resp);
                break;
            case GET_JSON_CAR_LIST:
                getCarList(req, resp);
                break;
            case GET_CLIENT_ORDERS:
                getClientOrders(req, resp);
                break;
            case GET_TAXI_ORDERS:
                getTaxiOrders(req, resp);
                break;
            case PAY_ORDER:
                payOrder(req, resp);
                break;
            case PRE_ORDER:
                preOrder(req, resp);
                break;
            case REJECT_ORDER:
                rejectOrder(req, resp);
                break;
            case NONE:
                resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * Used to get all orders information from specific client and sent it on client's orders page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void getClientOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(CLIENT)) {
            Client client = (Client) user;

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            DispatcherService dispatcherService = serviceFactory.getDispatcherService();
            List<Order> orderList = dispatcherService.getClientOrders(client);
            req.setAttribute(CLIENT_ORDER, orderList);
            req.getRequestDispatcher(CLIENT_ORDER_PAGE).forward(req, resp);
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * Used to get all orders information from specific taxi and sent it on taxi's orders page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void getTaxiOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(TAXI)) {
            Taxi taxi = (Taxi) user;

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            DispatcherService dispatcherService = serviceFactory.getDispatcherService();
            List<Order> orderList = dispatcherService.getTaxiOrders(taxi);

            req.setAttribute(TAXI_ORDER, orderList);
            req.getRequestDispatcher(TAXI_ORDER_PAGE).forward(req, resp);
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * Used to get available taxi list and sent it on call taxi page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void preOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(CLIENT)) {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserManagerService userManagerService = serviceFactory.getUserManagerService();
            List<Taxi> availableTaxiList = userManagerService.getAvailableTaxiList();

            req.setAttribute(AVAILABLE_TAXI, availableTaxiList);
            req.getRequestDispatcher(CALL_TAXI_PAGE).forward(req, resp);
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * receives all the information about the order. Writes an order to the database and assigns it an initial status.
     * It sends to the client's order page in case of success, otherwise you need to place the order again.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void callTaxi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(CLIENT)) {
            Client client = (Client) user;
            String sourceCoordinate = req.getParameter(SOURCE_COORD);
            String destinyCoordinate = req.getParameter(DESTINY_COORD);
            int taxiId = Integer.parseInt(req.getParameter(TAXI));
            Taxi taxi = (Taxi) new Taxi.TaxiBuilder().setId(taxiId).build();
            String bonusText = req.getParameter(BONUS);
            int bonus = Integer.parseInt(bonusText.isEmpty() ? ZERO_COUNT : bonusText);
            Double price = Double.parseDouble(req.getParameter(PRICE));

            Order order = new Order(client, taxi, sourceCoordinate, destinyCoordinate, price);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            DispatcherService dispatcherService = serviceFactory.getDispatcherService();
            if (dispatcherService.callConfirm(order, bonus)) {
                ((Client) req.getSession().getAttribute(USER)).setBonusPoints(client.getBonusPoints() - bonus);
                resp.sendRedirect(REQ_CLIENT_ORDER);
            } else {
                resp.sendRedirect(REQ_PRE_ORDER);
            }
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * receives an order id. Delete this order from database (without restriction).
     * In case of success, it sends to the client's order page otherwise you need to place the order again.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void deleteOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(ADMIN)) {
            String orderIdString = req.getParameter(ORDER_ID);
            int orderId = (orderIdString != null) ? Integer.parseInt(orderIdString) : INVALID_ORDER;

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            DispatcherService dispatcherService = serviceFactory.getDispatcherService();
            if (dispatcherService.changeOrderStatus(DELETE, orderId)) {
                resp.sendRedirect(REQ_ALL_ORDER);
            }
        }else{
            resp.sendRedirect(REDIRECT_HOME);
        }
    }


    /**
     * receives an order id. Delete this order from database.
     * So, if it been called by client, if it's success redirect client to client's order page.
     * If it been called by taxi driver, if it's success redirect taxi driver to taxi's order page.
     * In case of success, it sends to the client's order page otherwise you need to place the order again.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void cancelOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        String role = user.getRole();
        if (role.equals(CLIENT) || role.equals(TAXI)) {
            String orderIdString = req.getParameter(ORDER_ID);
            int orderId = (orderIdString != null) ? Integer.parseInt(orderIdString) : INVALID_ORDER;

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            DispatcherService dispatcherService = serviceFactory.getDispatcherService();
            if (dispatcherService.changeOrderStatus(CANCEL, orderId)) {
                if (role.equals(CLIENT)) {
                    resp.sendRedirect(REQ_CLIENT_ORDER);
                } else {
                    resp.sendRedirect(REQ_TAXI_ORDER);
                }
            } else {
                resp.sendRedirect(REDIRECT_HOME);
            }
        }else{
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * if (role.equals(UserEnum.ADMIN.getValue())){
     resp.sendRedirect(REQ_ALL_ORDER);
     * receives an order id. Method could been called by taxi.
     * Taxi confirm an order and it change status to "accepted". If it success redirect taxi to taxi's order page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void acceptOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(TAXI)) {
            String orderIdString = req.getParameter(ORDER_ID);
            int orderId = (orderIdString != null) ? Integer.parseInt(orderIdString) : INVALID_ORDER;

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            DispatcherService dispatcherService = serviceFactory.getDispatcherService();
            if (dispatcherService.changeOrderStatus(ACCEPT, orderId)) {
                resp.sendRedirect(REQ_TAXI_ORDER);
            } else {
                resp.sendRedirect(REDIRECT_HOME);
            }
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * receives an order id. Method could been called by taxi.
     * Taxi reject an order and it change status to "rejected". If it success redirect taxi to taxi's order page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void rejectOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        String role = user.getRole();
        if (role.equals(TAXI)) {
            String orderIdString = req.getParameter(ORDER_ID);
            int orderId = (orderIdString != null) ? Integer.parseInt(orderIdString) : INVALID_ORDER;

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            DispatcherService dispatcherService = serviceFactory.getDispatcherService();
            if (dispatcherService.changeOrderStatus(REJECT, orderId)) {
                resp.sendRedirect(REQ_TAXI_ORDER);
            } else {
                resp.sendRedirect(REDIRECT_HOME);
            }
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * receives an order id. Method could been called by client.
     * Client pay for the order and it change status to "completed". If it success redirect client to client's order page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void payOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        String role = user.getRole();
        if (role.equals(CLIENT)) {
            String orderIdString = req.getParameter(ORDER_ID);
            int orderId = (orderIdString != null) ? Integer.parseInt(orderIdString) : INVALID_ORDER;

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            DispatcherService dispatcherService = serviceFactory.getDispatcherService();
            if (dispatcherService.changeOrderStatus(PAY, orderId)) {
                resp.sendRedirect(REQ_CLIENT_ORDER);
            } else {
                resp.sendRedirect(REDIRECT_HOME);
            }
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * Use to get all orders information from all clients. Method could been called by admin.
     * As well build a pagination and sent it on admin's orders page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void getAllOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(ADMIN)) {
            String numPage = req.getParameter(NUM_PAGE);
            int page = (numPage != null) ? Integer.parseInt(numPage) : DEFAULT_PAGE;

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            DispatcherService dispatcherService = serviceFactory.getDispatcherService();
            List<Order> orderList = dispatcherService.getAllOrderList();


            PaginationService<Order> paginationService = serviceFactory.getPaginationService();
            paginationService.buildPagination(orderList);
            List<Order> pageOrderList = paginationService.getPagination().getPage(page);


            req.setAttribute(PAGE_ORDER_LIST, pageOrderList);
            req.setAttribute(PAGE_COUNT, paginationService.getPagination().getCountPages());
            req.setAttribute(CURRENT_PAGE, page);

            req.getRequestDispatcher(ADMIN_ORDER_PAGE).forward(req, resp);
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * Use to delete all information about orders. Method could been called by admin.
     * If it success redirect admin to admin's order page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void deleteObsoleteOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(ADMIN)) {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            DispatcherService dispatcherService = serviceFactory.getDispatcherService();
            if (dispatcherService.deleteObsoleteOrders()) {
                resp.sendRedirect(REQ_ALL_ORDER);
            }
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * Use to send information about all cars by ajax.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void getCarList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        TaxisService taxisService = serviceFactory.getTaxisService();
        List<Car> carList = taxisService.getCarList();

        Gson gson = new Gson();
        String carListJson = gson.toJson(carList);

        resp.setContentType(JSON_CONTENT);
        resp.getWriter().write(carListJson);
    }

    private enum DispatcherAction {
        PRE_ORDER("preOrder"),
        CALL_TAXI("callTaxi"),
        GET_CLIENT_ORDERS("getClientOrders"),
        GET_TAXI_ORDERS("getTaxiOrders"),
        CANCEL_ORDER("cancelOrder"),
        ACCEPT_ORDER("acceptOrder"),
        REJECT_ORDER("rejectOrder"),
        PAY_ORDER("payOrder"),
        GET_ALL_ORDERS("getAllOrders"),
        DELETE_ORDER("deleteOrder"),
        DELETE_OBSOLETE_ORDERS("deleteObsoleteOrders"),
        GET_JSON_CAR_LIST("getJsonCarList"),

        NONE("none");
        private String value;

        DispatcherAction(String value) {
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
        public static DispatcherAction getConstant(String action) {
            for (DispatcherAction each : DispatcherAction.values()) {
                if (each.getValue().equals(action)) {
                    return each;
                }
            }
            return NONE;
        }
    }
}
