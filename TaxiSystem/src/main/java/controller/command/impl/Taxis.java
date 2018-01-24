package controller.command.impl;

import controller.command.ControllerCommand;
import entity.Car;
import entity.User;
import service.ServiceFactory;
import service.TaxisService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Contain all actions related on taxi station request.
 */
public class Taxis implements ControllerCommand {
    private final static String REDIRECT_HOME = "Controller?method=signManager&action=goHomePage";
    /**
     * Realization of command pattern. Read a action parameter from request and execute special command depending on read parameter.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        TaxisAction taxisAction = TaxisAction.getConstant(action);
        switch (taxisAction){
            case GET_CAR_LIST:
                getCarList(req,resp);
                break;
            case ADD_CAR:
                addCar(req,resp);
                break;
            case REMOVE_CAR:
                removeCar(req,resp);
                break;
        }
    }
    /**
     * Use to get all information about all cars from database. Method could been called by admin.
     * If it success redirect admin to admin's cars page.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
    private void getCarList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole().equals("admin")) {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            TaxisService taxisService = serviceFactory.getTaxisService();
            List<Car> carList = taxisService.getCarList();

            req.setAttribute("carList", carList);
            req.getRequestDispatcher("WEB-INF/Admin/cars.jsp").forward(req, resp);
        }else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }
    /**
     * Use to add new car in database. Method could been called by admin.
     * If it success redirect admin to admin's cars page.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
    private void addCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole().equals("admin")) {
            String carNumber = req.getParameter("carNumber");
            String carName = req.getParameter("carName");
            String colour = req.getParameter("carColour");

            Car car = new Car(carNumber, carName, colour);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            TaxisService taxisService = serviceFactory.getTaxisService();
            if (taxisService.addCar(car)) {
                getCarList(req, resp);
            }
        }else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }
    /**
     * Use to delete information about special car from database. Method could been called by admin.
     * If it success redirect admin to admin's cars page.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
    private void removeCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole().equals("admin")) {
            String carNumber = req.getParameter("carNumber");
            Car car = new Car(carNumber);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            TaxisService taxisService = serviceFactory.getTaxisService();
            taxisService.removeCar(car);
            getCarList(req, resp);
        }else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    private enum TaxisAction{
        GET_CAR_LIST("getCarList"),
        ADD_CAR("addCar"),
        REMOVE_CAR("removeCar"),

        NONE("none");
        private String value;

        TaxisAction(String value){
            this.value = value;
        }
        /**
         * return stored in the enum value .
         * @return value.
         */
        public String getValue(){
            return value;
        }
        /**
         * In the dependence on the received value, return special enum.
         * @param action Special enum we are want to get.
         * @return get special enum in accordance with the action value.
         * If there are no matches return a NONE enum.
         */
        public static TaxisAction getConstant(String action){
            for (TaxisAction each: TaxisAction.values()){
                if(each.getValue().equals(action)){
                    return each;
                }
            }
            return NONE;
        }
    }
}
