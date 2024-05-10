package org.kharkiv.khpi.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kharkiv.khpi.model.Car;
import org.kharkiv.khpi.model.service.CarService;

import java.io.IOException;
import java.util.List;

public class CarServlet extends HttpServlet {

    @Inject
    private CarService carService;

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
            String[] carIds = req.getParameterValues("carIds");
            if (carIds != null) {
                for (String carId : carIds) {
                    String[] individualCarIds = carId.split(";");
                    for (String individualCarId : individualCarIds) {
                        carService.delete(Long.parseLong(individualCarId));
                    }
                }
            }
        } else if ("UPDATE".equals(action)) {
            String carIdsStr = req.getParameter("carIds");
            String make = req.getParameter("make");
            String type = req.getParameter("type");
            String plate = req.getParameter("plate");

            carService.update(Long.parseLong(carIdsStr), make, type, plate);
        } else {
            String make = req.getParameter("make");
            String type = req.getParameter("type");
            String plate = req.getParameter("plate");

            carService.save(make, type, plate);
        }
        resp.sendRedirect(req.getContextPath() + "/cars");
    }
}
