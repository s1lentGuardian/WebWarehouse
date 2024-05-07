package org.kharkiv.khpi.servlet.jpatest;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kharkiv.khpi.model.Goods;
import org.kharkiv.khpi.model.Supplier;
import org.kharkiv.khpi.model.repository.GoodsDAO;
import org.kharkiv.khpi.model.repository.SupplierDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;

public class GoodsTestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    private GoodsDAO goodsDAO;
    @Inject
    private SupplierDAO supplierDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("windows-1251");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head");
        sb.append("<body>");
        sb.append("<h2>Перевірка працездатності класів goods і GoodsDAO</h2>");
        sb.append("<ul>");

        long goodsId = createGoodsTest(sb);
        findByIdTest(sb, goodsId);
        findNameAndPrice(sb, goodsId);

        sb.append("</ul>");
        sb.append("</body>");
        sb.append("</html>");

        PrintWriter writer = resp.getWriter();
        writer.write(sb.toString());
    }

    private long createGoodsTest(StringBuilder sb) {
        Supplier supplier = supplierDAO.findById(6L);
        Goods goods = new Goods();
        goods.setTypeOfGoods("dummy");
        goods.setName("dummy");
        goods.setPrice(new BigDecimal("99"));
        goods.setSupplier(supplier);
        goodsDAO.save(goods);

        sb.append("<br><li>");
        sb.append("Перевірка додавання нового товару:");
        sb.append("<br>");
        sb.append("Первинний ключ доданого товару: " + goods.getGoodsId());
        sb.append("</li>");

        return goods.getGoodsId();
    }

    private void findByIdTest(StringBuilder sb, long goodsId) {
        sb.append("<br><li>");
        sb.append("Перевірка пошуку товару за первинним ключем:");
        sb.append("<br>");
        sb.append(goodsDAO.findById(goodsId).toString());
        sb.append("</li>");

    }

    private void findNameAndPrice(StringBuilder sb, long goodsId) {
        Map<String, String> result = goodsDAO.findNameAndPrice(goodsId);

        sb.append("<br><li>");
        sb.append("Перевірка пошуку окремих атрибутів сутності:");
        sb.append("<br>");
        sb.append("Ім'я товару: ").append(result.get("name"));
        sb.append("<br>");
        sb.append("Ціна: ").append(result.get("price"));
        sb.append("</li>");
    }
}
