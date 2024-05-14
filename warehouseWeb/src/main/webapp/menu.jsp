<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String user = (String) request.getAttribute("user");
%>
<html lang="en">
<head>
    <title>Menu Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        body, html {
            height: 100%;
            margin: 0;
            font-family: Arial, sans-serif;
            position: relative; /* Добавляем относительное позиционирование к body */
        }

        .container {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100%;
            background-image: url('https://www.amsc-usa.com/wp-content/uploads/2023/03/small-warehouse-2.jpg');
            background-size: 70%; /* Уменьшаем размер фонового изображения до 50% */
            background-position: center;
            background-repeat: no-repeat; /* Запрещаем повторение фонового изображения */
            position: relative; /* Добавляем относительное позиционирование к контейнеру */
        }

        h1 {
            font-size: 36px;
            margin-bottom: 30px;
            color: #fff; /* Цвет текста белый для контраста */
        }

        .links {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
        }

        a {
            margin: 1px;
            padding: 5px 10px;
        }

        .icon-img {
            position: absolute; /* Добавляем абсолютное позиционирование */
            top: 10px; /* Отступ сверху */
            right: 140px; /* Отступ справа */
            width: 40px; /* Ширина изображения */
            height: auto; /* Автоматическая высота */
        }

        .username {
            position: absolute; /* Добавляем абсолютное позиционирование */
            top: 20px; /* Отступ сверху */
            right: 0px; /* Отступ справа */
            color: #090000;
            font-size: 18px; /* Размер шрифта */
        }

        .logout-btn {
            position: absolute;
            top: 50px;
            right: 0;
        }
    </style>
</head>
<body>
<div class="container">
    <a href="https://cdn-icons-png.flaticon.com/512/149/149071.png"><img src="https://cdn-icons-png.flaticon.com/512/149/149071.png" class="icon-img"></a>
    <div class="username"><%= user %></div>
    <a href="logout" class="logout-btn"><button type="button" class="btn btn-outline-primary">Logout</button></a>
    <div class="links">
        <a href="suppliers"><button type="button" class="btn btn-primary">Suppliers</button></a>
        <a href="goods"><button type="button" class="btn btn-primary">Goods</button></a>
        <a href="warehouses"><button type="button" class="btn btn-primary">Warehouses</button></a>
        <a href="cars"><button type="button" class="btn btn-primary">Cars</button></a>
        <a href="transportations"><button type="button" class="btn btn-primary">Transportations</button></a>
    </div>
</div>
</body>
</html>
