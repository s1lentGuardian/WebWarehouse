package org.kharkiv.khpi.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.kharkiv.khpi.model.Car;
import org.kharkiv.khpi.model.exception.WarehouseConstraintViolationException;
import org.kharkiv.khpi.model.service.CarService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class CarServlet extends HttpServlet {

    @Inject
    private CarService carService;

    @Inject
    private Validator validator;

    public CarServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Car> cars = carService.findAllCars();
        request.setAttribute("cars", cars);
        request.getRequestDispatcher("car.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("ACTION");

        if ("REMOVE".equals(action)) {
            String[] carIds = req.getParameter("carIds").split(";");
            for (String carId : carIds) {
                carService.delete(Long.parseLong(carId));
            }

        } else if ("UPDATE".equals(action)) {
            String carIdsStr = req.getParameter("carIds");
            String make = req.getParameter("make");
            String type = req.getParameter("type");
            String plate = req.getParameter("plate");

            Car car = new Car(make, type, plate);

            Set<ConstraintViolation<Car>> violations = validator.validate(car);

            if (!violations.isEmpty()) {
                req.setAttribute("violations", violations);
                throw new WarehouseConstraintViolationException(violations);
            } else {
                carService.update(Long.parseLong(carIdsStr), make, type, plate);
            }
        } else {
            String make = req.getParameter("make");
            String type = req.getParameter("type");
            String plate = req.getParameter("plate");

            Car car = new Car(make, type, plate);

            Set<ConstraintViolation<Car>> violations = validator.validate(car);

            if (!violations.isEmpty()) {
                req.setAttribute("violations", violations);
                throw new WarehouseConstraintViolationException(violations);
            } else {
                carService.save(car);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/cars");
    }
}
