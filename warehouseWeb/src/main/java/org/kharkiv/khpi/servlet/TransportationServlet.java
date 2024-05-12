package org.kharkiv.khpi.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kharkiv.khpi.model.Car;
import org.kharkiv.khpi.model.Goods;
import org.kharkiv.khpi.model.Transportation;
import org.kharkiv.khpi.model.Warehouse;
import org.kharkiv.khpi.model.exception.WarehouseNumberFormatException;
import org.kharkiv.khpi.model.service.CarService;
import org.kharkiv.khpi.model.service.GoodsService;
import org.kharkiv.khpi.model.service.TransportationService;
import org.kharkiv.khpi.model.service.WarehouseService;

import java.io.IOException;
import java.util.List;

public class TransportationServlet extends HttpServlet {

    @Inject
    private TransportationService transportationService;

    @Inject
    private CarService carService;

    @Inject
    private GoodsService goodsService;

    @Inject
    private WarehouseService warehouseService;

    public TransportationServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Transportation> transportations = transportationService.findAllTransportations();
        List<Car> cars = carService.findAllCars();
        List<Goods> goods = goodsService.findAllGoods();
        List<Warehouse> warehouses = warehouseService.findAllWarehouses();

        request.setAttribute("transportations", transportations);
        request.setAttribute("carsCollection", cars);
        request.setAttribute("goodsList", goods);
        request.setAttribute("warehouseCollection", warehouses);

        request.getRequestDispatcher("transportation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("ACTION");

        if ("REMOVE".equals(action)) {
            String[] transportationIds = req.getParameterValues("transportationIds");
            if (transportationIds != null) {
                for (String transportationId : transportationIds) {
                    String[] individualTransportationIds = transportationId.split(";");
                    for (String individualTransportationId : individualTransportationIds) {
                        transportationService.delete(Long.parseLong(individualTransportationId));
                    }
                }
            }
        } else if ("UPDATE".equals(action)) {
            try {
                String transportationIdStr = req.getParameter("transportationId");
                Long transportationId = Long.parseLong(transportationIdStr);

                String carIdStr = req.getParameter("carId");
                Long carId = Long.parseLong(carIdStr);

                String goodsIdStr = req.getParameter("goodsId");
                Long goodsId = Long.parseLong(goodsIdStr);

                String countStr = req.getParameter("count");
                Integer count = Integer.parseInt(countStr);

                String pickFromWarIdStr = req.getParameter("pickFromWar");
                Long pickFromWarId = Long.parseLong(pickFromWarIdStr);

                String bringToWarIdStr = req.getParameter("bringToWar");
                Long bringToWarId = Long.parseLong(bringToWarIdStr);

                String dateStr = req.getParameter("date");

                transportationService.update(transportationId, carId, goodsId, count, pickFromWarId, bringToWarId, dateStr);
            } catch (RuntimeException e) {
                throw new WarehouseNumberFormatException(e.getMessage());
            }
        } else {
            try {
                String carIdStr = req.getParameter("carId");
                Long carId = Long.parseLong(carIdStr);

                String goodsIdStr = req.getParameter("goodsId");
                Long goodsId = Long.parseLong(goodsIdStr);

                String countStr = req.getParameter("count");
                Integer count = Integer.parseInt(countStr);

                String pickFromWarIdStr = req.getParameter("pickFromWar");
                Long pickFromWarId = Long.parseLong(pickFromWarIdStr);

                String bringToWarIdStr = req.getParameter("bringToWar");
                Long bringToWarId = Long.parseLong(bringToWarIdStr);

                String dateStr = req.getParameter("date");

                transportationService.save(carId, goodsId, count, pickFromWarId, bringToWarId, dateStr);
            } catch (RuntimeException e) {
                throw new WarehouseNumberFormatException(e.getMessage());
            }
        }
        resp.sendRedirect(req.getContextPath() + "/transportations");
    }
}
