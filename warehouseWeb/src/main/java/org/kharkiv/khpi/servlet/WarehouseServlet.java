package org.kharkiv.khpi.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kharkiv.khpi.model.Goods;
import org.kharkiv.khpi.model.Warehouse;
import org.kharkiv.khpi.model.service.GoodsService;
import org.kharkiv.khpi.model.service.WarehouseService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class WarehouseServlet extends HttpServlet {

    @Inject
    private WarehouseService warehouseService;

    @Inject
    private GoodsService goodsService;

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
            String[] warehouseIds = req.getParameterValues("warehouseIds");
            if (warehouseIds != null) {
                for (String warehouseId : warehouseIds) {
                    String[] individualWarehouseIds = warehouseId.split(";");
                    for (String individualWarehouseId : individualWarehouseIds) {
                        warehouseService.delete(Long.parseLong(individualWarehouseId));
                    }
                }
            }
        } else {

            String name = req.getParameter("warName");

            String id = req.getParameter("goodsId");
            Long goodsId = Long.parseLong(id);

            String numberOfPlacesStr = req.getParameter("numberOfPlaces");
            Long numberOfPlaces = Long.parseLong(numberOfPlacesStr);

            String country = req.getParameter("country");
            String city = req.getParameter("city");
            String address = req.getParameter("address");

            warehouseService.save(name, numberOfPlaces, country, city, address, goodsId);
        }
        resp.sendRedirect(req.getContextPath() + "/warehouses");
    }
}
