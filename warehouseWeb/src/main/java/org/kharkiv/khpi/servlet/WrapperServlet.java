package org.kharkiv.khpi.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Сервлет, який емулює login request від користувача.
 */
public class WrapperServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**
         * Створюємо обгортку над отриманим запитом. Коли інший вебкомпонент буде зчитувати параметр 'userName' з
         * запиту, наша обгортка буде повертати значення 'wrapperDemo'.
         */
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(req) {
            @Override
            public String getParameter(String name) {
                if (name.equals("userName")) {
                    return "wrapperDemo";
                } else {
                    return super.getParameter(name);
                }
            }
        };

        //переадресуємо запис на login servlet
        req.getRequestDispatcher("/login").forward(requestWrapper, resp);
    }
}
