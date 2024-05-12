<%@ page import="java.util.List" %>
<%@ page import="org.kharkiv.khpi.model.Transportation" %>
<%@ page import="org.kharkiv.khpi.model.Car" %>
<%@ page import="org.kharkiv.khpi.model.Goods" %>
<%@ page import="org.kharkiv.khpi.model.Warehouse" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Transportation> transportations = (List<Transportation>) request.getAttribute("transportations");
    List<Car> cars = (List<Car>) request.getAttribute("carsCollection");
    List<Goods> goods = (List<Goods>) request.getAttribute("goodsList");
    List<Warehouse> warehouses = (List<Warehouse>) request.getAttribute("warehouseCollection");
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
            position: relative; /* Добавлено для позиционирования изображения */
        }

        table {
            background-color: #fdfcfc;
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
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 60%;
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);
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

        /* Стили для изображения */
        .top-image {
            position: absolute;
            top: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 50%; /* Ширина изображения */
            z-index: -1; /* Отправляем изображение за пределы контейнера */
        }
    </style>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<a href="homePage.html">Home</a>

<div class="container">
    <img class="top-image" src="https://logixgrid.com/wp-content/uploads/2020/02/Cover-1-1.png" alt="Warehouse Image">
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
            <th>Номер машини</th>
            <th>Назва товару</th>
            <th>Загрузка</th>
            <th>Розвантаження</th>
            <th>Кількість товару</th>
            <th>Дата</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Transportation transportation : transportations)
            {
        %>
        <tr>
            <td><input type="checkbox" value="<%= transportation.getTransportationId()%>"
                       data-car-id="<%= transportation.getCar().getId() %>"
                       data-pickFromWar-id="<%= transportation.getPickUpFromWarehouse().getWarehouseId() %>"
                       data-bringToWar-id="<%= transportation.getBringToWarehouse().getWarehouseId() %>"></td>
            <td><%= transportation.getTransportationId()%></td>
            <td><%= transportation.getCar().getLicensePlateNumber()%></td>

            <% for (Goods warehouseGoods : transportation.getGoods())
            {
            %>
            <td><%= warehouseGoods.getName()%></td>
            <%
            }
            %>
            <td><%= transportation.getPickUpFromWarehouse().getName()%></td>
            <td><%= transportation.getBringToWarehouse().getName()%></td>
            <td><%= transportation.getCount()%></td>
            <td><%= transportation.getDate()%></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <div class="btn-group">
        <button type="button" class="btn btn-success" id="showFormBtn">Додати</button>

        <form method="post" action="transportations">
            <input type="hidden" name="ACTION" value="REMOVE">
            <input id="transportationIds" name="transportationIds" type="hidden">
            <button type="button" class="btn btn-danger" id="deleteBtn">Видалити</button>
        </form>

        <button id="updateBtn" type="button" class="btn btn-primary">Оновити</button>
    </div>

    <div id="myModal" class="modal">
        <div class="modal-content">
            <form id="createUpdateForm" method="post" action="transportations">
                <label for="carId">Айді машини</label>
                <select id="carId" name="carId">
                    <% for(Car car : cars) { %>
                    <option value="<%= car.getId() %>"><%= car.getLicensePlateNumber() %></option>
                    <% } %>
                </select><br>
                <label for="goodsId">Товар</label>
                <select id="goodsId" name="goodsId">
                    <% for(Goods goodsItem : goods) { %>
                    <option value="<%= goodsItem.getGoodsId() %>"><%= goodsItem.getName() %></option>
                    <% } %>
                </select><br>

                <label for="count">Кількість товару</label>
                <input id="count" name="count"><br>

                <label for="pickFromWar">Загрузка з складу</label>
                <select id="pickFromWar" name="pickFromWar">
                    <% for(Warehouse pickFromWar : warehouses) { %>
                    <option value="<%= pickFromWar.getWarehouseId() %>"><%= pickFromWar.getName() %></option>
                    <% } %>
                </select><br>

                <label for="bringToWar">Розвантаження у склад</label>
                <select id="bringToWar" name="bringToWar">
                    <% for(Warehouse bringToWar : warehouses) { %>
                    <option value="<%= bringToWar.getWarehouseId() %>"><%= bringToWar.getName() %></option>
                    <% } %>
                </select><br>

                <label for="date">Дата</label>
                <input id="date" name="date" aria-describedby="dateDescribedby">
                <div id="dateDescribedby" class="form-text error-text">* Введіть у форматі рік-місяць-день (yyyy-MM-dd)</div><br>


                <button type="submit" class="btn btn-primary d-inline-block">Створити</button>
            </form>
        </div>
    </div>
</div>



<script>
    //DELETE
    document.getElementById("deleteBtn").addEventListener("click", function () {
        var checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
        var transportationIds = [];
        checkboxes.forEach(function (checkbox) {
            transportationIds.push(checkbox.value);
        });

        if (transportationIds.length === 0) {
            alert("Виберіть перевезення для видалення.");
            return;
        }

        document.getElementById("transportationIds").value = transportationIds.join(';');
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
            var transportationId = selectedCheckbox.value;
            var carId = selectedCheckbox.dataset.carId;
            var pickFromWarId = selectedCheckbox.dataset.pickFromWarId;
            var bringToWarId = selectedCheckbox.dataset.bringToWarId;


            var transportationRow = document.querySelector('input[value="' + transportationId + '"]').parentNode.parentNode;
            var transportationData = {
                id: transportationRow.querySelector('td:nth-child(2)').innerText,
                carId: transportationRow.querySelector('td:nth-child(3)').innerText,
                goodsId: transportationRow.querySelector('td:nth-child(4)').innerText,
                pickFromWar: transportationRow.querySelector('td:nth-child(5)').innerText,
                bringToWar: transportationRow.querySelector('td:nth-child(6)').innerText,
                count: transportationRow.querySelector('td:nth-child(7)').innerText,
                date: transportationRow.querySelector('td:nth-child(8)').innerText,
            };

            document.getElementById('carId').value = carId;
            document.getElementById('goodsId').value = transportationData.goodsId;
            document.getElementById('pickFromWar').value = pickFromWarId;
            document.getElementById('bringToWar').value = bringToWarId;
            document.getElementById('count').value = transportationData.count;
            document.getElementById('date').value = transportationData.date;




            var form = document.getElementById('createUpdateForm');
            var actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'ACTION';
            actionInput.value = 'UPDATE';
            form.appendChild(actionInput);

            var transportationIdsInput = document.createElement('input');
            transportationIdsInput.type = 'hidden';
            transportationIdsInput.name = 'transportationId';
            transportationIdsInput.value = transportationId;
            form.appendChild(transportationIdsInput);
        } else {
            alert("Виберіть перевезення для оновлення.");
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



















