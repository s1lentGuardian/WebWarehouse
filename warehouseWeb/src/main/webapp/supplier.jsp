<%@ page import="java.util.List" %>
<%@ page import="org.kharkiv.khpi.model.Supplier" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    List<Supplier> suppliers = (List<Supplier>) request.getAttribute("suppliers");
%>

<html lang="en">
<head>
    <title>Supplier Page</title>
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
            <th>Країна</th>
            <th>Місто</th>
            <th>Номер телефону</th>
        </tr>
        </thead>
        <tbody>
        <% for (Supplier supplier : suppliers) { %>
        <tr>
            <td><input type="checkbox" value="<%= supplier.getSupplierId()%>"></td>
            <td><%= supplier.getSupplierId() %></td>
            <td><%= supplier.getCountry() %></td>
            <td><%= supplier.getCity() %></td>
            <td><%= supplier.getPhoneNumber() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <div class="btn-group">
        <button type="button" class="btn btn-success" id="showFormBtn">Додати</button>

        <form method="post" action="suppliers">
            <input type="hidden" name="ACTION" value="REMOVE">
            <input id="supplierIds" name="supplierIds" type="hidden">
            <button type="submit" class="btn btn-danger" id="deleteBtn">Видалити</button>
        </form>

        <button id="updateBtn" type="button" class="btn btn-primary">Оновити</button>
    </div>

    <div id="myModal" class="modal">
        <div class="modal-content">
            <form id="createUpdateForm" method="post" action="suppliers">

                <label for="country">Країна</label>
                <input id="country" name="country"><br>

                <label for="city">Місто</label>
                <input id="city" name="city"><br>

                <label for="phoneNumber">Номер телефону</label>
                <input id="phoneNumber" name="phoneNumber"><br>

                <button type="submit" class="btn btn-primary d-inline-block" >Створити</button>
            </form>
        </div>
    </div>
</div>


<script>
    //DELETE
    document.getElementById("deleteBtn").addEventListener("click", function () {
        var checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
        var suppliersIds = [];
        checkboxes.forEach(function (checkbox) {
            suppliersIds.push(checkbox.value);
        });

        if (suppliersIds.length === 0) {
            alert("Виберіть постачальника для видалення.");
            return;
        }

        document.getElementById("supplierIds").value = suppliersIds.join(';');
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
            var supplierId = selectedCheckbox.value;

            var supplierRow = document.querySelector('input[value="' + supplierId + '"]').parentNode.parentNode;
            var supplierData = {
                id: supplierRow.querySelector('td:nth-child(2)').innerText,
                country: supplierRow.querySelector('td:nth-child(3)').innerText,
                city: supplierRow.querySelector('td:nth-child(4)').innerText,
                phoneNumber: supplierRow.querySelector('td:nth-child(5)').innerText
            };
            document.getElementById('country').value = supplierData.country;
            document.getElementById('city').value = supplierData.city;
            document.getElementById('phoneNumber').value = supplierData.phoneNumber;



            var form = document.getElementById('createUpdateForm');
            var actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'ACTION';
            actionInput.value = 'UPDATE';
            form.appendChild(actionInput);

            var supplierIdsInput = document.createElement('input');
            supplierIdsInput.type = 'hidden';
            supplierIdsInput.name = 'supplierIds';
            supplierIdsInput.value = supplierId;
            form.appendChild(supplierIdsInput);
        } else {
            alert("Виберіть постачальника для оновлення.");
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
