package org.kharkiv.khpi.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.kharkiv.khpi.model.Supplier;
import org.kharkiv.khpi.model.service.SupplierService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class SupplierServlet extends HttpServlet {

    @Inject
    private SupplierService supplierService;

    @Inject
    private Validator validator;

    public SupplierServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Supplier> suppliers = supplierService.findAllSuppliers();
        request.setAttribute("suppliers", suppliers);
        request.getRequestDispatcher("supplier.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("ACTION");

        if ("REMOVE".equals(action)) {
            String[] suppliersIds = req.getParameter("supplierIds").split(";");
            for (String supplierId : suppliersIds) {
                supplierService.delete(Long.parseLong(supplierId));
            }

        } else if ("UPDATE".equals(action)) {
            String supplierId = req.getParameter("supplierIds");
            String country = req.getParameter("country");
            String city = req.getParameter("city");
            String phoneNumber = req.getParameter("phoneNumber");

            Supplier supplier = new Supplier(country, city, phoneNumber);

            Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);

            if (!violations.isEmpty()) {
                req.setAttribute("violations", violations);
                req.getRequestDispatcher("notValid.jsp").forward(req, resp);
            } else {
                supplierService.update(Long.parseLong(supplierId), country, city, phoneNumber);
            }
        } else {
            String country = req.getParameter("country");
            String city = req.getParameter("city");
            String phoneNumber = req.getParameter("phoneNumber");

            Supplier supplier = new Supplier(country, city, phoneNumber);

            Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);

            if (!violations.isEmpty()) {
                req.setAttribute("violations", violations);
                req.getRequestDispatcher("notValid.jsp").forward(req, resp);
            } else {
                supplierService.save(supplier);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/suppliers");
    }
}
