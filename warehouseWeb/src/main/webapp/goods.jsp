<%@ page import="java.util.List" %>
<%@ page import="org.kharkiv.khpi.model.Goods" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Goods> goods = (List<Goods>) request.getAttribute("goods");
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Goods Page</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            font-family: Arial, sans-serif;
        }
        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100%;
            flex-direction: column;
            position: relative;
        }

        table {
            border-collapse: collapse;
            width: 80%;
            margin-bottom: 30px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto; /* Добавлено */
            background-color: rgba(0, 0, 0, 0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 60%;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            max-height: 70vh; /* Добавлено */
            overflow-y: auto; /* Добавлено */
        }

        form label, form input {
            display: block;
            margin-bottom: 10px;
        }

        .btn-group {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .btn {
            margin-right: 10px;
        }

        .top-image {
            position: absolute;
            top: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 50%;
            z-index: -1;
        }
    </style>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<a href="homePage.html">Home</a>

<div class="container">
    <img class="top-image" src="https://www.advotics.com/wp-content/uploads/2022/04/5-Langkah-Penting-untuk-Menjaga-Kualitas-Produk-di-Gudang-01-1024x554.jpg" alt="Warehouse Image">
    <br>
    <br>
    <br>
    <br>
    <br>
    <table>
        <thead>
        <tr>
            <th> </th>
            <th>Айді</th>
            <th>Тип товару</th>
            <th>Назва</th>
            <th>Ціна</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Goods goodsItem : goods)
            {
        %>
        <tr>
            <td><input type="checkbox" value="<%= goodsItem.getGoodsId()%>"></td>
            <td><%= goodsItem.getGoodsId()%></td>
            <td><%= goodsItem.getTypeOfGoods()%></td>
            <td><%= goodsItem.getName()%></td>
            <td><%= goodsItem.getPrice()%></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <div class="btn-group">
        <button type="button" class="btn btn-success" id="showFormBtn">Додати</button>

        <form method="post" action="goods">
            <input name="ACTION" value="REMOVE" type="hidden">
            <input id="goodsIds" name="goodsIds" type="hidden">
            <button type="submit" class="btn btn-danger" id="deleteBtn">Видалити</button>
        </form>

        <button type="button" class="btn btn-primary">Оновити</button>
        <button type="button" class="btn btn-info">Додаткова Інформація</button>
    </div>

    <div id="myModal" class="modal">
        <div class="modal-content">
            <form id="goodsForm" method="post" action="goods">

                <label for="supplierCountry">Країна постачальника</label>
                <input id="supplierCountry" name="supplierCountry"><br>

                <label for="supplierCity">Місто постачальника</label>
                <input id="supplierCity" name="supplierCity"><br>

                <label for="supplierPhoneNumber">Номер телефону постачальника</label>
                <input id="supplierPhoneNumber" name="supplierPhoneNumber"><br>

                <label for="typeOfGoods">Тип товару</label>
                <input id="typeOfGoods" name="typeOfGoods"><br>

                <label for="name">Назва</label>
                <input id="name" name="name"><br>

                <label for="price">Ціна</label>
                <input id="price" name="price"><br>

                <button type="submit" class="btn btn-primary d-inline-block">Створити</button>
            </form>
        </div>
    </div>
    <div id="additionalInfoModal" class="modal">
        <div class="modal-content">
            <h2 style="text-align: center;">Постачальник</h2>
            <table width="100%" style="margin: 0 auto;">
                <tbody>
                <tr>
                    <td>Айді</td>
                    <td>Країна</td>
                    <td>Компанія</td>
                    <td>Телефон</td>
                </tr>
                <%
                    for (Goods goodsItem : goods)
                    {
                %>
                <tr>
                    <td><%= goodsItem.getSupplier().getSupplierId()%></td>
                    <td><%= goodsItem.getSupplier().getCountry() %></td>
                    <td><%= goodsItem.getSupplier().getCity() %></td>
                    <td><%= goodsItem.getSupplier().getPhoneNumber()%></td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script>
    //DELETE
    document.getElementById("deleteBtn").addEventListener("click", function () {
        var checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
        var goodsIds = [];
        checkboxes.forEach(function (checkbox) {
            goodsIds.push(checkbox.value);
        });

        if (goodsIds.length === 0) {
            alert("Виберіть товари для видалення.");
            return;
        }

        document.getElementById("goodsIds").value = goodsIds.join(';');
        document.querySelector('form').submit();
    });




    // Оголошуємо модальне вікно як змінну, щоб мати до нього доступ
    var modal = document.getElementById("myModal");

    // Функція, що відкриває модальне вікно
    function openModal() {
        modal.style.display = "block";
    }

    // При кліку на кнопку "Додати товар"
    document.getElementById("showFormBtn").addEventListener("click", openModal);

    // При кліку на кнопку "Оновити"
    document.querySelector('.btn-primary').addEventListener('click', function() {
        // Отримати всі чекбокси
        var checkboxes = document.querySelectorAll('input[type="checkbox"]');
        // Перевірити, чи хоча б один чекбокс вибраний
        var atLeastOneChecked = false;
        checkboxes.forEach(function(checkbox) {
            if (checkbox.checked) {
                atLeastOneChecked = true;
            }
        });
        // Якщо хоча б один чекбокс вибраний, відкрити модальне вікно
        if (atLeastOneChecked) {
            openModal();
        }
    });

    // Закриття модального вікна при кліку поза ним
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    };

    // Логіка видалення рядків аналогічна як раніше
    document.querySelector('.btn-danger').addEventListener('click', function() {
        var checkboxes = document.querySelectorAll('input[type="checkbox"]');
        checkboxes.forEach(function(checkbox) {
            if (checkbox.checked) {
                checkbox.parentElement.parentElement.remove();
            }
        });
    });

    var additionalInfoModal = document.getElementById("additionalInfoModal");

    // Функція, що відкриває модальне вікно з додатковою інформацією
    function openAdditionalInfoModal() {
        // Отримати всі чекбокси
        var checkboxes = document.querySelectorAll('input[type="checkbox"]');
        // Перевірити, чи хоча б один чекбокс вибраний
        var atLeastOneChecked = false;
        checkboxes.forEach(function(checkbox) {
            if (checkbox.checked) {
                atLeastOneChecked = true;
            }
        });
        // Якщо хоча б один чекбокс вибраний, відкрити модальне вікно з додатковою інформацією
        if (atLeastOneChecked) {
            additionalInfoModal.style.display = "block";
        }
    }
    // При кліку на кнопку "Додаткова Інформація"
    document.querySelector('.btn-info').addEventListener('click', openAdditionalInfoModal);

    // Закриття модального вікна з додатковою інформацією при кліку поза ним
    window.onclick = function(event) {
        if (event.target === additionalInfoModal) {
            additionalInfoModal.style.display = "none";
        }
    };
</script>
</body>
</html>
