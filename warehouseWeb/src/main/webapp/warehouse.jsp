<%@ page import="java.util.List" %>
<%@ page import="org.kharkiv.khpi.model.Warehouse" %>
<%@ page import="org.kharkiv.khpi.model.Goods" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Warehouse> warehouses = (List<Warehouse>) request.getAttribute("warehouses");
%>
<%
    List<Goods> goods = (List<Goods>) request.getAttribute("goodsCollection");
%>
<html lang="en">
<head>
    <title>Goods Page</title>
    <style>
        .error-text {
            color: red;
        }

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
    <img class="top-image"
         src="https://img.freepik.com/premium-vector/warehouse-building-trucks-load-machines_80590-523.jpg"
         alt="Warehouse Image">
    <br>
    <br>
    <br>
    <br>
    <br>
    <table>
        <thead>
        <tr>
            <th></th>
            <th>Айді</th>
            <th>Назва</th>
            <th>Кількість місць</th>
            <th>Країна</th>
            <th>Місто</th>
            <th>Адреса</th>
            <th>Товари</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Warehouse warehouse : warehouses) {
        %>
        <tr>
            <td><input type="checkbox" value="<%= warehouse.getWarehouseId()%>"></td>
            <td><%= warehouse.getWarehouseId()%>
            </td>
            <td><%= warehouse.getName()%>
            </td>
            <td><%= warehouse.getNumberPlaces()%>
            </td>
            <td><%= warehouse.getCountry()%>
            </td>
            <td><%= warehouse.getCity()%>
            </td>
            <td><%= warehouse.getAddressLocation()%>
            </td>

            <td>
                <select>
                    <% for (Goods goodsItem : warehouse.getGoods()) { %>
                    <option><%= goodsItem.getName() %></option>
                    <% } %>
                </select>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <div class="btn-group">
        <button type="submit" class="btn btn-success" id="showFormBtn">Додати</button>

        <form method="post" action="warehouses">
            <input type="hidden" name="ACTION" value="REMOVE">
            <input id="warehouseIds" name="warehouseIds" type="hidden">
            <button type="submit" class="btn btn-danger" id="deleteBtn">Видалити</button>
        </form>
        <button id="updateBtn" type="button" class="btn btn-primary">Оновити</button>
    </div>

    <div id="myModal" class="modal">
        <div class="modal-content">
            <form id="createUpdateForm" method="post" action="warehouses">
                <label for="warName">Назва складу</label>
                <input id="warName" name="warName"><br>

                <label for="goodsId">Айді товарів:</label>
                <select id="goodsId" name="goodsId" multiple>
                    <% for (Goods goodsItem : goods) { %>
                    <option value="<%= goodsItem.getGoodsId() %>"><%= goodsItem.getName() %>
                    </option>
                    <% } %>
                </select>
                <br>

                <label for="numberOfPlaces">Кількість місць</label>
                <input id="numberOfPlaces" name="numberOfPlaces" aria-describedby="numberOfPlacesDescribedby">
                <div id="numberOfPlacesDescribedby" class="form-text error-text">* Введіть число</div>
                <br>

                <label for="country">Країна</label>
                <input id="country" name="country"><br>

                <label for="city">Місто</label>
                <input id="city" name="city"><br>

                <label for="address">Адреса</label>
                <input id="address" name="address"><br>

                <button type="submit" class="btn btn-primary d-inline-block">Створити</button>
            </form>
        </div>
    </div>
</div>


<script>
    //DELETE
    document.getElementById("deleteBtn").addEventListener("click", function () {
        var checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
        var warehouseIds = [];
        checkboxes.forEach(function (checkbox) {
            warehouseIds.push(checkbox.value);
        });

        if (warehouseIds.length === 0) {
            alert("Виберіть Склади для видалення.");
            return;
        }

        document.getElementById("warehouseIds").value = warehouseIds.join(';');
        document.querySelector('form').submit();
    });

    //UPDATE
    document.getElementById("updateBtn").addEventListener('click', function () {
        // Отримати всі чекбокси
        var checkboxes = document.querySelectorAll('input[type="checkbox"]');
        // Знайти выбранный чекбокс
        var selectedCheckbox;
        checkboxes.forEach(function (checkbox) {
            if (checkbox.checked) {
                selectedCheckbox = checkbox;
            }
        });
        // Проверить, выбран ли один чекбокс
        if (selectedCheckbox) {
            var warehouseId = selectedCheckbox.value;


            var warehouseRow = document.querySelector('input[value="' + warehouseId + '"]').parentNode.parentNode;
            // Получить данные автомобиля из строки
            var warehouseData = {
                id: warehouseRow.querySelector('td:nth-child(2)').innerText,
                warName: warehouseRow.querySelector('td:nth-child(3)').innerText,
                numberOfPlaces: warehouseRow.querySelector('td:nth-child(4)').innerText,
                country: warehouseRow.querySelector('td:nth-child(5)').innerText,
                city: warehouseRow.querySelector('td:nth-child(6)').innerText,
                address: warehouseRow.querySelector('td:nth-child(7)').innerText,
                goods: Array.from(warehouseRow.querySelectorAll('td:nth-child(8)')).map(td => td.innerText)

            };
            // Отобразить данные автомобиля в модальном окне
            document.getElementById('warName').value = warehouseData.warName;
            document.getElementById('numberOfPlaces').value = warehouseData.numberOfPlaces;
            document.getElementById('country').value = warehouseData.country;
            document.getElementById('city').value = warehouseData.city;
            document.getElementById('address').value = warehouseData.address;
            document.getElementById('goodsId').value = warehouseData.goodsId;
            document.getElementById('goodsId').value = warehouseData.goods.join(', ');


            var form = document.getElementById('createUpdateForm');
            // Создать скрытое поле для ACTION
            var actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'ACTION';
            actionInput.value = 'UPDATE';
            form.appendChild(actionInput);

            var warehouseIdsInput = document.createElement('input');
            warehouseIdsInput.type = 'hidden';
            warehouseIdsInput.name = 'warehouseId';
            warehouseIdsInput.value = warehouseId;
            // Добавить скрытое поле в форму
            form.appendChild(warehouseIdsInput);
        } else {
            alert("Виберіть склад для оновлення.");
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

    // При кліку на кнопку "Оновити" відкрити модальне вікно з формую
    document.getElementById("updateBtn").addEventListener('click', function () {
        // Отримати всі чекбокси
        var checkboxes = document.querySelectorAll('input[type="checkbox"]');
        // Перевірити, чи хоча б один чекбокс вибраний
        var atLeastOneChecked = false;
        checkboxes.forEach(function (checkbox) {
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
    window.onclick = function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    };
</script>
</body>
</html>
