package org.kharkiv.khpi.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class
 *
 * Використовуємо анотацію WebServlet або ж описуємо сервлет в
 * дескрипторі розгортання web.xml (елементи servlet і servlet-mapping).
 * Перший елемент відповідає за вказівку вебконтейнеру, що клас є сервлетом.
 * Другий елемент вказує аліас (URL), за яким сервлет буде доступний.
 */

public class GoodsPageServlet extends HttpServlet {

    public GoodsPageServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Варіанти встановлення кодування виведення.
        //Обов'язково перед першим використанням вихідного потоку,
        //інакше отримаємо крокозябри в браузері
        response.setContentType("text/html; charset=UTF-8");

        //формує відповідь - html-сторінку
        String html = getHtml();

        //Отримуємо вихідний потік. Усе, що в нього буде надруковано, буде відправлено клієнту
        PrintWriter pw = response.getWriter();
        pw.write(html);

        //Закриваємо вихідний потік
        pw.close();
    }

    private String getHtml() {
        return """
                <!DOCTYPE html>
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
                        <tr>
                            <td><input type="checkbox"></td>
                            <td>1</td>
                            <td>Стиральна машинка</td>
                            <td>Bosh</td>
                            <td>999$</td>
                        </tr>
                        <tr>
                            <td><input type="checkbox"></td>
                            <td>2</td>
                            <td>Посудомийна машина</td>
                            <td>Bosh</td>
                            <td>899$</td>
                        </tr>
                        <tr>
                            <td><input type="checkbox"></td>
                            <td>3</td>
                            <td>Холодильник</td>
                            <td>Samsung</td>
                            <td>990$</td>
                        </tr>
                        </tbody>
                    </table>
                            
                    <div class="btn-group">
                        <button type="button" class="btn btn-success" id="showFormBtn">Додати</button>
                        <button type="button" class="btn btn-danger">Видалити</button>
                        <button type="button" class="btn btn-primary">Оновити</button>
                        <button type="button" class="btn btn-info">Додаткова Інформація</button>
                            
                    </div>
                            
                    <div id="myModal" class="modal">
                        <div class="modal-content">
                            <form id="goodsForm">
                            
                                <label for="supplierCountry">Країна постачальника</label>
                                <input id="supplierCountry" name="supplierCountry"><br>
                            
                                <label for="supplierCity">Місто постачальника</label>
                                <input id="supplierCity" name="supplierCity"><br>
                            
                                <label for="supplierCompany">Компанія постачальника</label>
                                <input id="supplierCompany" name="supplierCompany"><br>
                            
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
                                    <td>1</td>
                                </tr>
                                <tr>
                                    <td>Країна</td>
                                    <td>Польща</td>
                                </tr>
                                <tr>
                                    <td>Компанія</td>
                                    <td>Wizard</td>
                                </tr>
                                <tr>
                                    <td>Телефон</td>
                                    <td>+3509834943</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                            
                            
                            
                <script>
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
                            
                """;
    }

}
