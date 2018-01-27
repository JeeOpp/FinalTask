package controller.command.impl;

import controller.command.ControllerCommand;
import entity.Car;
import entity.User;
import entity.entityEnum.CarEnum;
import entity.entityEnum.UserEnum;
import service.ServiceFactory;
import service.TaxisService;
import service.impl.TaxisServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Contain all actions related on taxi station request.
 * Delegates business logic to the {@link TaxisService}
 * The class implements {@link ControllerCommand}.
 */
public class Taxis implements ControllerCommand {
    private final static String REDIRECT_HOME = "Controller?method=signManager&action=goHomePage";
    private final static String ADMIN_CARS_PAGE = "WEB-INF/Admin/cars.jsp";
    private final static String GET_CAR_PAGE = "Controller?method=taxis&action=getCarList";

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
        TaxisAction taxisAction = TaxisAction.getConstant(action);
        switch (taxisAction) {
            case GET_CAR_LIST:
                getCarList(req, resp);
                break;
            case ADD_CAR:
                addCar(req, resp);
                break;
            case REMOVE_CAR:
                removeCar(req, resp);
                break;
        }
    }

    /**
     * Used to get all information about all cars from database. Method could been called by admin.
     * If it success redirect admin to admin's cars page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void getCarList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(UserEnum.USER.getValue());
        if (user.getRole().equals(UserEnum.ADMIN.getValue())) {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            TaxisService taxisService = serviceFactory.getTaxisService();
            List<Car> carList = taxisService.getCarList();

            req.setAttribute(CarEnum.CAR_LIST.getValue(), carList);
            req.getRequestDispatcher(ADMIN_CARS_PAGE).forward(req, resp);
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * Used to add new car in database. Method could been called by admin.
     * If it success redirect admin to admin's cars page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void addCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(UserEnum.USER.getValue());
        if (user.getRole().equals(UserEnum.ADMIN.getValue())) {
            String carNumber = req.getParameter(CarEnum.CAR_NUMBER.getValue());
            String carName = req.getParameter(CarEnum.CAR_NAME.getValue());
            String colour = req.getParameter(CarEnum.CAR_COLOUR.getValue());

            Car car = new Car(carNumber, carName, colour);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            TaxisService taxisService = serviceFactory.getTaxisService();
            if (taxisService.addCar(car)) {
                resp.sendRedirect(GET_CAR_PAGE);
            }
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * Use to delete information about special car from database. Method could been called by admin.
     * If it success redirect admin to admin's cars page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void removeCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(UserEnum.USER.getValue());
        if (user.getRole().equals(UserEnum.ADMIN.getValue())) {
            String carNumber = req.getParameter(CarEnum.CAR_NUMBER.getValue());
            Car car = new Car(carNumber);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            TaxisService taxisService = serviceFactory.getTaxisService();
            taxisService.removeCar(car);
            resp.sendRedirect(GET_CAR_PAGE);
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    private enum TaxisAction {
        GET_CAR_LIST("getCarList"),
        ADD_CAR("addCar"),
        REMOVE_CAR("removeCar"),

        NONE("none");
        private String value;

        TaxisAction(String value) {
            this.value = value;
        }

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
        public static TaxisAction getConstant(String action) {
            for (TaxisAction each : TaxisAction.values()) {
                if (each.getValue().equals(action)) {
                    return each;
                }
            }
            return NONE;
        }
    }
}