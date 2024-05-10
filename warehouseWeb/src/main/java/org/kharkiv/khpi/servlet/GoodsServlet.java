package org.kharkiv.khpi.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kharkiv.khpi.model.Goods;
import org.kharkiv.khpi.model.service.GoodsService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet implementation class
 * <p>
 * Використовуємо анотацію WebServlet або ж описуємо сервлет в
 * дескрипторі розгортання web.xml (елементи servlet і servlet-mapping).
 * Перший елемент відповідає за вказівку вебконтейнеру, що клас є сервлетом.
 * Другий елемент вказує аліас (URL), за яким сервлет буде доступний.
 */

public class GoodsServlet extends HttpServlet {

    @Inject
    private GoodsService goodsService;

    public GoodsServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Goods> goods = goodsService.findAllGoods();
        request.setAttribute("goods", goods);
        request.getRequestDispatcher("goods.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("ACTION");

        if ("REMOVE".equals(action)) {
            String[] goodsIds = req.getParameterValues("goodsIds");
            if (goodsIds != null) {
                for (String goodsId : goodsIds) {
                    String[] individualGoodsIds = goodsId.split(";");
                    for (String individualGoodsId : individualGoodsIds) {
                        goodsService.delete(Long.parseLong(individualGoodsId));
                    }
                }
            }
        } else {
            String supplierCountry = req.getParameter("supplierCountry");
            String supplierCity = req.getParameter("supplierCity");
            String supplierPhoneNumber = req.getParameter("supplierPhoneNumber");
            String typeOfGoods = req.getParameter("typeOfGoods");
            String name = req.getParameter("name");
            String price = req.getParameter("price");

            goodsService.save(typeOfGoods, name, price, supplierCountry, supplierCity, supplierPhoneNumber);
        }
        resp.sendRedirect(req.getContextPath() + "/goods");
    }
}
