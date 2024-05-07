package org.kharkiv.khpi.servlet.jpatest;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kharkiv.khpi.model.Warehouse;
import org.kharkiv.khpi.model.repository.GoodsDAO;
import org.kharkiv.khpi.model.repository.WarehouseDAO;
import org.kharkiv.khpi.model.service.WarehouseService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class WarehouseTestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    private WarehouseDAO warehouseDAO;
    @Inject
    private GoodsDAO goodsDAO;

    @Inject
    private WarehouseService warehouseService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("windows-1251");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head");
        sb.append("<body>");
        sb.append("<h2>Перевірка працездатності класів Warehouse і WarehouseDAO</h2>");
        sb.append("<ul>");

        long warehouseId = createWarehouseTest(sb);
        findByIdTest(sb, warehouseId);
        findCityAndAddressLocation(sb, warehouseId);

        sb.append("</ul>");
        sb.append("</body>");
        sb.append("</html>");

        PrintWriter writer = resp.getWriter();
        writer.write(sb.toString());
    }

    private long createWarehouseTest(StringBuilder sb) {
        Warehouse warehouse = warehouseService.createWarehouse();

        sb.append("<br><li>");
        sb.append("Перевірка додавання нового складу:");
        sb.append("<br>");
        sb.append("Первинний ключ доданого складу: " + warehouse.getWarehouseId());
        sb.append("</li>");

        return warehouse.getWarehouseId();
    }

    private void findByIdTest(StringBuilder sb, long warehouseId) {
        sb.append("<br><li>");
        sb.append("Перевірка пошуку складу за первинним ключем:");
        sb.append("<br>");
        sb.append(warehouseDAO.findById(warehouseId).toString());
        sb.append("</li>");

    }

    private void findCityAndAddressLocation(StringBuilder sb, long warehouseId) {
        Map<String, String> result = warehouseDAO.findCityAndAddressLocation(warehouseId);

        sb.append("<br><li>");
        sb.append("Перевірка пошуку окремих атрибутів сутності:");
        sb.append("<br>");
        sb.append("Місто: ").append(result.get("city"));
        sb.append("<br>");
        sb.append("Адреса: ").append(result.get("addressLocation"));
        sb.append("</li>");
    }
}
