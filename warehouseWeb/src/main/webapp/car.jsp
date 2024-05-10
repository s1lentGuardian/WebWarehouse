<%@ page import="java.util.List" %>
<%@ page import="org.kharkiv.khpi.model.Car" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Car> cars = (List<Car>) request.getAttribute("cars");
%>
<html lang="en">
<head>
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
            background-color: rgba(0, 0, 0, 0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 60%;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
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
    <img class="top-image" src="https://www.avonsolutions.com/wp-content/uploads/2021/08/WAREHOUSE_MANAGEMENT-final.jpg"
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
            <th>Марка</th>
            <th>Тип</th>
            <th>Номер</th>
        </tr>
        </thead>
        <tbody>
        <% for (Car car : cars) { %>
        <tr>
            <td><input type="checkbox" value="<%= car.getId()%>"></td>
            <td><%= car.getId() %></td>
            <td><%= car.getMake() %></td>
            <td><%= car.getTypeOfCar() %></td>
            <td><%= car.getLicensePlateNumber() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <div class="btn-group">
        <button type="button" class="btn btn-success" id="showFormBtn">Додати</button>

        <form method="post" action="cars">
            <input type="hidden" name="ACTION" value="REMOVE">
            <input id="carIds" name="carIds" type="hidden">
            <button type="submit" class="btn btn-danger" id="deleteBtn">Видалити</button>
        </form>

        <button id="updateBtn" type="button" class="btn btn-primary">Оновити</button>
    </div>

    <div id="myModal" class="modal">
        <div class="modal-content">
            <form id="createUpdateForm" method="post" action="cars">

                <label for="make">Марка</label>
                <input id="make" name="make"><br>

                <label for="type">Тип машини</label>
                <input id="type" name="type"><br>

                <label for="plate">Номер</label>
                <input id="plate" name="plate"><br>

                <button type="submit" class="btn btn-primary d-inline-block" >Створити</button>
            </form>
        </div>
    </div>
</div>


<script>
    //DELETE
    document.getElementById("deleteBtn").addEventListener("click", function () {
        var checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
        var carIds = [];
        checkboxes.forEach(function (checkbox) {
            carIds.push(checkbox.value);
        });

        if (carIds.length === 0) {
            alert("Виберіть автомобілі для видалення.");
            return;
        }

        document.getElementById("carIds").value = carIds.join(';');
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
            // Значение выбранного чекбокса будет carId
            var carId = selectedCheckbox.value;



            //ДЛЯ ВІДОБРАЖЕННЯ ДАНИХ ОБ'ЄКТА МАШИНИ У МОДАЛЬНІЙ ФОРМІ ПРИ НАТИСКАННІ НА ОНОВИТИ
            //получим выбранный чекбокс, получим id выбранного автомобиля, найдем соответствующую строку в таблице и извлечем данные этого автомобиля. Затем отобразим эти данные в модальном окне.
            // Найти строку с данными выбранного автомобиля
            var carRow = document.querySelector('input[value="' + carId + '"]').parentNode.parentNode;
            // Получить данные автомобиля из строки
            var carData = {
                id: carRow.querySelector('td:nth-child(2)').innerText,
                make: carRow.querySelector('td:nth-child(3)').innerText,
                type: carRow.querySelector('td:nth-child(4)').innerText,
                plate: carRow.querySelector('td:nth-child(5)').innerText
            };
            // Отобразить данные автомобиля в модальном окне
            document.getElementById('make').value = carData.make;
            document.getElementById('type').value = carData.type;
            document.getElementById('plate').value = carData.plate;



            var form = document.getElementById('createUpdateForm');
            // Создать скрытое поле для ACTION
            var actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'ACTION';
            actionInput.value = 'UPDATE';
            // Добавить скрытое поле в форму
            form.appendChild(actionInput);
            // Создать скрытое поле для carIds
            var carIdsInput = document.createElement('input');
            carIdsInput.type = 'hidden';
            carIdsInput.name = 'carIds';
            // Установить значение скрытого поля carIds
            carIdsInput.value = carId;
            // Добавить скрытое поле в форму
            form.appendChild(carIdsInput);
        } else {
            alert("Виберіть автомобіль для оновлення.");
        }
    });











    // Модальне вікно
    var modal = document.getElementById("myModal");

    // Функція, що відкриває модальне вікно
    function openModal() {
        modal.style.display = "block";
    }

    // При кліку на кнопку "Додати товар" відкрити модальне вікно з формую
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
