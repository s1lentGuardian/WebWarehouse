package org.kharkiv.khpi.servlet.jpatest;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kharkiv.khpi.model.Car;
import org.kharkiv.khpi.model.Goods;
import org.kharkiv.khpi.model.Transportation;
import org.kharkiv.khpi.model.Warehouse;
import org.kharkiv.khpi.model.repository.CarDAO;
import org.kharkiv.khpi.model.repository.GoodsDAO;
import org.kharkiv.khpi.model.repository.TransportationDAO;
import org.kharkiv.khpi.model.repository.WarehouseDAO;
import org.kharkiv.khpi.model.service.TransportationService;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TransportationTestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    private TransportationDAO transportationDAO;

    @Inject
    private TransportationService transportationService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("windows-1251");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head");
        sb.append("<body>");
        sb.append("<h2>Перевірка працездатності класів Transportation і TransportationDAO</h2>");
        sb.append("<ul>");

        long transportationId = createTransportationTest(sb);
        findByIdTest(sb, transportationId);
        findDateAndCar(sb, transportationId);

        sb.append("</ul>");
        sb.append("</body>");
        sb.append("</html>");

        PrintWriter writer = resp.getWriter();
        writer.write(sb.toString());
    }

    private long createTransportationTest(StringBuilder sb) {
        Transportation transportation = transportationService.createTransportation();

        sb.append("<br><li>");
        sb.append("Перевірка додавання нового перевезення:");
        sb.append("<br>");
        sb.append("Дата доданого перевезення: " + transportation.getDate());
        sb.append("</li>");
        return transportation.getTransportationId();
    }

    private void findByIdTest(StringBuilder sb, long transportationId) {
        sb.append("<br><li>");
        sb.append("Перевірка пошуку перевезення за первинним ключем:");
        sb.append("<br>");
        sb.append(transportationDAO.findById(transportationId).toString());
        sb.append("</li>");

    }

    private void findDateAndCar(StringBuilder sb, long transportationId) {
        Map<String, String> result = transportationDAO.findDateAndCar(transportationId);

        sb.append("<br><li>");
        sb.append("Перевірка пошуку окремих атрибутів сутності:");
        sb.append("<br>");
        sb.append("Дата перевезення: ").append(result.get("date"));
        sb.append("<br>");
        sb.append("Машина: ").append(result.get("car"));
        sb.append("</li>");
    }
}
