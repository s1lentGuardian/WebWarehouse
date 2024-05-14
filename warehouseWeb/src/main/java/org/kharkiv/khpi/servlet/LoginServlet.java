package org.kharkiv.khpi.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.kharkiv.khpi.model.UserEntity;
import org.kharkiv.khpi.model.exception.UserNotFoundException;
import org.kharkiv.khpi.model.exception.UsernamePasswordEmptyException;
import org.kharkiv.khpi.model.service.UserService;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private UserService userService;

    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        //Отримання http-сесії якщо вона є
        HttpSession session = req.getSession(false);

        if (session == null) {
            //отримання із запиту імені користувача
            String userName = req.getParameter("userName");
            String userPassword = req.getParameter("userPassword");

            if (userName == null || userName.isEmpty()) {
                //перевірка наявності імені, якщо нема - кидаємо виключення
                throw new UsernamePasswordEmptyException();
            }

            UserEntity user = userService.findByUsername(userName);

            if (user == null) {
                throw new UserNotFoundException("User with such username doesn't exist");
            }

            if (!user.getPassword().equals(userPassword)) {
                throw new UsernamePasswordEmptyException();
            }

            //створення сесії
            session = req.getSession();

            //збереження в сесії отриманого імені
            session.setAttribute("user", userName);

        }

        /* отримання диспетчера запитів для сервлета меню із запиту
         * Примітка: тут ми можемо використовувати відносні шляхи. */
        RequestDispatcher rd = req.getRequestDispatcher("menu");

//        /*перенаправлення запиту до сервлету меню. URL в адресному рядку
//         * при цьому не змінюється. Може призводити до проблем, якщо користувач
//         * оновлюватиме сторінку */
        rd.forward(req, resp);

        /* Інший варіант перенаправлення користувача на сервлет статистики.
         * Тут URL в адресному рядку змінюється. */
//		resp.sendRedirect("menu");

    }
}
