<%@ page import="java.util.List" %>
<%@ page import="org.kharkiv.khpi.model.Goods" %>
<%@ page import="org.kharkiv.khpi.model.Supplier" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Goods> goods = (List<Goods>) request.getAttribute("goods");
    List<Supplier> suppliers = (List<Supplier>) request.getAttribute("suppliers");
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Goods Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
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
            <th>Постачальник</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Goods goodsItem : goods)
            {
        %>
        <tr>
            <td><input type="checkbox" value="<%= goodsItem.getGoodsId()%>" data-supplier-id="<%= goodsItem.getSupplier().getSupplierId() %>"></td>
            <td><%= goodsItem.getGoodsId()%></td>
            <td><%= goodsItem.getTypeOfGoods()%></td>
            <td><%= goodsItem.getName()%></td>
            <td><%= goodsItem.getPrice()%></td>
            <td><%= goodsItem.getSupplier().getPhoneNumber()%></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <div style="display: flex; gap: 10px;">
        <button type="button" class="btn btn-success" id="showFormBtn">Додати</button>

        <form method="post" action="goods">
            <input name="ACTION" value="REMOVE" type="hidden">
            <input id="goodsIds" name="goodsIds" type="hidden">
            <button type="submit" class="btn btn-danger" id="deleteBtn">Видалити</button>
        </form>

        <button id="updateBtn" type="button" class="btn btn-primary">Оновити</button>
    </div>

    <div id="myModal" class="modal">
        <div class="modal-content">
            <form id="createUpdateForm" method="post" action="goods">

                <label for="supplierId">Постачальник</label>
                <select id="supplierId" name="supplierId">
                    <% for(Supplier supplier : suppliers) { %>
                    <option value="<%= supplier.getSupplierId() %>"><%= supplier.getPhoneNumber()%></option>
                    <% } %>
                </select><br>

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

    //UPDATE
    document.getElementById("updateBtn").addEventListener('click', function () {
        var checkboxes = document.querySelectorAll('input[type="checkbox"]');
        var selectedCheckbox;
        checkboxes.forEach(function (checkbox) {
            if (checkbox.checked) {
                selectedCheckbox = checkbox;
            }
        });
        if (selectedCheckbox) {
            var goodsId = selectedCheckbox.value;
            var supplierId = selectedCheckbox.dataset.supplierId; // Получить ID поставщика из атрибута data-supplier-id

            var goodsRow = document.querySelector('input[value="' + goodsId + '"]').parentNode.parentNode;
            var carData = {
                id: goodsRow.querySelector('td:nth-child(2)').innerText,
                typeOfGoods: goodsRow.querySelector('td:nth-child(3)').innerText,
                name: goodsRow.querySelector('td:nth-child(4)').innerText,
                price: goodsRow.querySelector('td:nth-child(5)').innerText,
                supplierId: goodsRow.querySelector('td:nth-child(6)').innerText
            };
            document.getElementById('typeOfGoods').value = carData.typeOfGoods;
            document.getElementById('name').value = carData.name;
            document.getElementById('price').value = carData.price;
            document.getElementById('supplierId').value = supplierId;



            var form = document.getElementById('createUpdateForm');
            var actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'ACTION';
            actionInput.value = 'UPDATE';
            form.appendChild(actionInput);

            var goodsIdsInput = document.createElement('input');
            goodsIdsInput.type = 'hidden';
            goodsIdsInput.name = 'goodsId';
            goodsIdsInput.value = goodsId;
            form.appendChild(goodsIdsInput);
        } else {
            alert("Виберіть товар для оновлення.");
        }
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
    document.getElementById("updateBtn").addEventListener('click', function () {
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


</script>
</body>
</html>
