package org.kharkiv.khpi.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.kharkiv.khpi.model.Goods;
import org.kharkiv.khpi.model.Supplier;
import org.kharkiv.khpi.model.exception.WarehouseConstraintViolationException;
import org.kharkiv.khpi.model.exception.WarehouseNumberFormatException;
import org.kharkiv.khpi.model.service.GoodsService;
import org.kharkiv.khpi.model.service.SupplierService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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

    @Inject
    private SupplierService supplierService;

    @Inject
    private Validator validator;

    public GoodsServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Goods> goods = goodsService.findAllGoods();
        List<Supplier> suppliers = supplierService.findAllSuppliers();

        request.setAttribute("goods", goods);
        request.setAttribute("suppliers", suppliers);
        request.getRequestDispatcher("goods.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("ACTION");

        if ("REMOVE".equals(action)) {
            String[] goodsIds = req.getParameter("goodsIds").split(";");
            for (String goodsId : goodsIds) {
                goodsService.delete(Long.parseLong(goodsId));
            }

        } else if ("UPDATE".equals(action)) {
            try {
                String supplierId = req.getParameter("supplierId");
                String goodsId = req.getParameter("goodsId");
                String typeOfGoods = req.getParameter("typeOfGoods");
                String name = req.getParameter("name");
                String price = req.getParameter("price");

                Goods goods = new Goods(typeOfGoods, name, new BigDecimal(price));

                Set<ConstraintViolation<Goods>> violations = validator.validate(goods);

                if (!violations.isEmpty()) {
                    req.setAttribute("violations", violations);
                    throw new WarehouseConstraintViolationException(violations);
                } else {
                    goodsService.update(Long.parseLong(goodsId), typeOfGoods, name, price, Long.parseLong(supplierId));
                }
            } catch (RuntimeException e) {
                throw new WarehouseNumberFormatException(e.getMessage());
            }
        } else {
            try {
                String supplierId = req.getParameter("supplierId");
                String typeOfGoods = req.getParameter("typeOfGoods");
                String name = req.getParameter("name");
                String price = req.getParameter("price");

                Goods goods = new Goods(typeOfGoods, name, new BigDecimal(price));

                Set<ConstraintViolation<Goods>> violations = validator.validate(goods);

                if (!violations.isEmpty()) {
                    req.setAttribute("violations", violations);
                    throw new WarehouseConstraintViolationException(violations);
                } else {
                    goodsService.save(goods, Long.parseLong(supplierId));
                }
            } catch (RuntimeException e) {
                throw new WarehouseNumberFormatException(e.getMessage());
            }
        }
        resp.sendRedirect(req.getContextPath() + "/goods");
    }
}
