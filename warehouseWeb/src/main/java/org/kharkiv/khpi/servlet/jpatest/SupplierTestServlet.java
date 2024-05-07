package org.kharkiv.khpi.servlet.jpatest;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kharkiv.khpi.model.Supplier;
import org.kharkiv.khpi.model.repository.SupplierDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class SupplierTestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private SupplierDAO supplierDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("windows-1251");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head");
        sb.append("<body>");
        sb.append("<h2>Перевірка працездатності класів Supplier і SupplierDAO</h2>");
        sb.append("<ul>");

        long supplierId = createSupplierTest(sb);
        findByIdTest(sb, supplierId);
        findCityAndPhoneNumber(sb, supplierId);

        sb.append("</ul>");
        sb.append("</body>");
        sb.append("</html>");

        PrintWriter writer = resp.getWriter();
        writer.write(sb.toString());
    }

    private long createSupplierTest(StringBuilder sb) {
        Supplier supplier = new Supplier();
        supplier.setCountry("dummy");
        supplier.setCity("dummy");
        supplier.setPhoneNumber("dummy");
        supplierDAO.save(supplier);

        sb.append("<br><li>");
        sb.append("Перевірка додавання нового постачальника:");
        sb.append("<br>");
        sb.append("Первинний ключ доданого постачальника: " + supplier.getSupplierId());
        sb.append("</li>");

        return supplier.getSupplierId();
    }

    private void findByIdTest(StringBuilder sb, long supplierId) {
        sb.append("<br><li>");
        sb.append("Перевірка пошуку постачальника за первинним ключем:");
        sb.append("<br>");
        sb.append(supplierDAO.findById(supplierId).toString());
        sb.append("</li>");

    }

    private void findCityAndPhoneNumber(StringBuilder sb, long supplierId) {
        Map<String, String> result = supplierDAO.findCityAndPhoneNumber(supplierId);

        sb.append("<br><li>");
        sb.append("Перевірка пошуку окремих атрибутів сутності:");
        sb.append("<br>");
        sb.append("Місто: ").append(result.get("city"));
        sb.append("<br>");
        sb.append("Номер: ").append(result.get("phoneNumber"));
        sb.append("</li>");
    }
}
