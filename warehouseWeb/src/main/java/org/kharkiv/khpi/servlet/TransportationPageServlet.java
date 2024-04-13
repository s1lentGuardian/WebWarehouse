package org.kharkiv.khpi.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class TransportationPageServlet extends HttpServlet {

    public TransportationPageServlet() {
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
                            <th>Дата</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><input type="checkbox"></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
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
                            <form>
                                <label for="carId">Айді машини</label>
                                <select id="carId" name="carId">
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                </select><br>
                                <label for="goodsId">Айді товарів</label>
                                <select id="goodsId" name="goodsId">
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                </select><br>
                                
                                <label for="pickFromWar">Загрузка з складу</label>
                                <select id="pickFromWar" name="pickFromWar">
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                </select><br>
                                
                                <label for="bringFromWar">Розвантаження у склад</label>
                                <select id="bringFromWar" name="bringFromWar">
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                </select><br>
                                
                                <button type="submit" class="btn btn-primary d-inline-block">Створити</button>
                            </form>
                        </div>
                    </div>
                    <div id="additionalInfoModal" class="modal">
                        <div class="modal-content">
                            <table width="100%" style="margin: 0 auto;">
                                <tbody>
                                <tr>
                                    <th colspan="2">Товар</th>
                                </tr>
                                <tr>
                                    <td>Тип товару</td>
                                    <td>Пральна машинка</td>
                                </tr>
                                <tr>
                                    <td>Назва</td>
                                    <td>Bosh</td>
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
