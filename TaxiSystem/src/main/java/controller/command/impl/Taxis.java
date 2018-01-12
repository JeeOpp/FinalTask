package controller.command.impl;

import controller.command.ControllerCommand;
import entity.Car;
import service.ServiceFactory;
import service.TaxisService;
import service.UserManagerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by DNAPC on 03.01.2018.
 */
public class Taxis implements ControllerCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        SwitchConstant switchConstant = SwitchConstant.getConstant(action);
        switch (switchConstant){
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

    private void getCarList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        TaxisService taxisService = serviceFactory.getTaxisService();
        List<Car> carList = taxisService.getCarList();

        req.setAttribute("carList", carList);
        req.getRequestDispatcher("WEB-INF/Admin/cars.jsp").forward(req, resp);
    }
    private void addCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carNumber = req.getParameter("carNumber");
        String carName = req.getParameter("carName");
        String colour = req.getParameter("carColour");

        Car car = new Car(carNumber,carName,colour);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        TaxisService taxisService = serviceFactory.getTaxisService();
        if(taxisService.addCar(car)){
            getCarList(req,resp);
        }
    }
    private void removeCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carNumber = req.getParameter("carNumber");
        Car car = new Car(carNumber);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        TaxisService taxisService = serviceFactory.getTaxisService();
        if(taxisService.removeCar(car)){
            getCarList(req,resp);
        }
        getCarList(req,resp);
    }

}
