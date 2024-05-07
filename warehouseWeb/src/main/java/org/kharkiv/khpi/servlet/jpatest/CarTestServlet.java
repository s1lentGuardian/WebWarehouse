package org.kharkiv.khpi.servlet.jpatest;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kharkiv.khpi.model.Car;
import org.kharkiv.khpi.model.repository.CarDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class CarTestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    private CarDAO carDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("windows-1251");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head");
        sb.append("<body>");
        sb.append("<h2>Перевірка працездатності класів Car і CarDAO</h2>");
        sb.append("<ul>");

        long carId = createCarTest(sb);
        findByIdTest(sb, carId);
        findMakeAndLicensePlateNumber(sb, carId);

        sb.append("</ul>");
        sb.append("</body>");
        sb.append("</html>");

        PrintWriter writer = resp.getWriter();
        writer.write(sb.toString());
    }

    /**
     * Перевірка додавання нової машини в БД
     * @return первинний ключ створеної машини
     * */
    private long createCarTest(StringBuilder sb) {
        Car car = new Car();
        car.setMake("dummy");
        car.setTypeOfCar("dummy");
        car.setLicensePlateNumber("dummy");
        carDAO.save(car);

        sb.append("<br><li>");
        sb.append("Перевірка додавання нової машини:");
        sb.append("<br>");
        sb.append("Первинний ключ доданої машини: " + car.getId());
        sb.append("</li>");

        return car.getId();
    }

    /**
     * Перевірка пошуку машини за первинним ключем
     * */
    private void findByIdTest(StringBuilder sb, long carId) {
        sb.append("<br><li>");
        sb.append("Перевірка пошуку машини за первинним ключем:");
        sb.append("<br>");
        sb.append(carDAO.findById(carId).toString());
        sb.append("</li>");

    }

    /**
     * Перевірка пошуку окремих атрибутів сутності
     * */
    private void findMakeAndLicensePlateNumber(StringBuilder sb, long carId) {
        Map<String, String> result = carDAO.findMakeAndLicensePlateNumber(carId);

        sb.append("<br><li>");
        sb.append("Перевірка пошуку окремих атрибутів сутності:");
        sb.append("<br>");
        sb.append("Марка машини: ").append(result.get("make"));
        sb.append("<br>");
        sb.append("Номер: ").append(result.get("licensePlateNumber"));
        sb.append("</li>");
    }
}
