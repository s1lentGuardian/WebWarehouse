package org.kharkiv.khpi.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.kharkiv.khpi.model.Goods;
import org.kharkiv.khpi.model.Warehouse;
import org.kharkiv.khpi.model.exception.WarehouseConstraintViolationException;
import org.kharkiv.khpi.model.exception.WarehouseNumberFormatException;
import org.kharkiv.khpi.model.service.GoodsService;
import org.kharkiv.khpi.model.service.WarehouseService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class WarehouseServlet extends HttpServlet {

    @Inject
    private WarehouseService warehouseService;

    @Inject
    private GoodsService goodsService;

    @Inject
    private Validator validator;

    public WarehouseServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Warehouse> warehouses = warehouseService.findAllWarehouses();
        List<Goods> goods = goodsService.findAllGoods();
        request.setAttribute("warehouses", warehouses);
        request.setAttribute("goodsCollection", goods);
        request.getRequestDispatcher("warehouse.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("ACTION");

        if ("REMOVE".equals(action)) {
            String[] warehouseIds = req.getParameter("warehouseIds").split(";");
            for (String warehouseId : warehouseIds) {
                warehouseService.delete(Long.parseLong(warehouseId));

            }
        } else if ("UPDATE".equals(action)) {
            try {
                String warehouseIdStr = req.getParameter("warehouseId");
                String name = req.getParameter("warName");

                String id = req.getParameter("goodsId");
                Long goodsId = Long.parseLong(id);

                String numberOfPlacesStr = req.getParameter("numberOfPlaces");
                Long numberOfPlaces = Long.parseLong(numberOfPlacesStr);

                String country = req.getParameter("country");
                String city = req.getParameter("city");
                String address = req.getParameter("address");

                Warehouse warehouse = new Warehouse(name, numberOfPlaces, country, city, address);
                Set<ConstraintViolation<Warehouse>> violations = validator.validate(warehouse);

                if (!violations.isEmpty()) {
                    req.setAttribute("violations", violations);
                    throw new WarehouseConstraintViolationException(violations);
                } else {
                    warehouseService.update(Long.parseLong(warehouseIdStr), name, numberOfPlaces, country, city, address, goodsId);
                }
            } catch (RuntimeException e) {
                throw new WarehouseNumberFormatException(e.getMessage());
            }
        } else {

            try {
                String name = req.getParameter("warName");
                String[] goodsIds = req.getParameterValues("goodsId");

                String numberOfPlacesStr = req.getParameter("numberOfPlaces");
                Long numberOfPlaces = Long.parseLong(numberOfPlacesStr);

                String country = req.getParameter("country");
                String city = req.getParameter("city");
                String address = req.getParameter("address");

                Warehouse warehouse = new Warehouse(name, numberOfPlaces, country, city, address);
                Set<ConstraintViolation<Warehouse>> violations = validator.validate(warehouse);

                if (!violations.isEmpty()) {
                    req.setAttribute("violations", violations);
                    throw new WarehouseConstraintViolationException(violations);
                } else {
                    warehouseService.save(warehouse, goodsIds);
                }
            } catch (RuntimeException e) {
                throw new WarehouseNumberFormatException(e.getMessage());
            }
        }
        resp.sendRedirect(req.getContextPath() + "/warehouses");
    }
}
