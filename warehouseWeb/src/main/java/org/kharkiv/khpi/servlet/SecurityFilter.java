package org.kharkiv.khpi.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Security filter: перенаправляє неаутентифікованих користувачів
 * на початкову сторінку, якщо вони намагаються відкрити захищені ресурси.
 * */
public class SecurityFilter extends HttpFilter {

    public SecurityFilter() {
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("SecurityFilter triggered for " + req.getRequestURI());

        /*Перевірка наявності сесії. Якщо її немає,
         * то до інших сторінок користувач звертається минаючи login.html */
        HttpSession session = req.getSession(false);

        if (session == null) {
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // Цей заголовок каже браузере не кешувати відповідь. Це для того щоб не з'являлася сторінка логування після переходу на різні ресурси після успішного логування
            RequestDispatcher rd = req.getRequestDispatcher("login.html");
            rd.forward(req, res);
        } else {
            // Сесія є, передаємо запит далі по ланцюжку фільтрів
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}
