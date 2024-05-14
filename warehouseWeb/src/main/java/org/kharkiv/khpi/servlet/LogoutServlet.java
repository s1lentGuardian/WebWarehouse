package org.kharkiv.khpi.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Сервлет, який відповідає за видалення сесії.
 * УВАГА: можлива проблема через те, що браузер може закешувати 'logout' сторінку. Через це 'logout' request
 * може не надсилатися на сервер, а замість цього браузер буде відображати збережену копію.
 */
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LogoutServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Отримання сесії. Якщо її немає, то не створюємо
        HttpSession session = request.getSession(false);

        if (session != null) {
            String user = (String) session.getAttribute("user");

            //Знищення сесії
            session.invalidate();

            request.getRequestDispatcher("login.html").forward(request, response);
        }
    }
}
