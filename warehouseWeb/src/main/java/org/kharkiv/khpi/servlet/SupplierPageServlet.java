package org.kharkiv.khpi.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class SupplierPageServlet extends HttpServlet {

    public SupplierPageServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        String html = getHtml();

        PrintWriter pw = response.getWriter();
        pw.write(html);
        pw.close();
    }

    private String getHtml() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Supplier Page</title>
                </head>
                <body>
                <a href="homePage.html">Home</a>
                <h1>Welcome to Supplier Page!</h1>
                </body>
                </html>
                """;
    }
}
