<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.Set" %>
<%@ page import="jakarta.validation.ConstraintViolation" %>
<%@ page import="org.kharkiv.khpi.model.Car" %>

<%--відображення помилок у вигляді списка--%>
<%
    Set<ConstraintViolation<Car>> violations = (Set<ConstraintViolation<Car>>) request.getAttribute("violations");
    if (violations != null && !violations.isEmpty()) {
        out.println("<ul>");
        for (ConstraintViolation<Car> violation : violations) {
            out.println("<li>" + violation.getMessage() + "</li>");
        }
        out.println("</ul>");
    }
%>
<html>
<head>
    <title>Not Valid</title>
</head>
<body>

</body>
</html>
