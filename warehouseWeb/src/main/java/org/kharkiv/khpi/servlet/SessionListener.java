package org.kharkiv.khpi.servlet;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * Клас-слухач життєвого циклу сесії.
 * */
@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        //Встановлюємо час життя сесії в 120 секунд.
        //Якщо протягом цього часу не буде нових запитів від користувача,
        //сесію буде автоматично знищено.
        event.getSession().setMaxInactiveInterval(120);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        //Отримуємо ім'я залогіненого користувача
        String user = (String) event.getSession().getAttribute("user");

        System.out.println("Знищення сесії для користувача: " + user);
    }
}
