package org.kharkiv.khpi.model.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.kharkiv.khpi.model.Car;
import org.kharkiv.khpi.model.Transportation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * DAO клас для роботи з таблицею transportation
 */
@Stateless
public class TransportationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Пошук сутності за первинним ключем
     * @param id первинний ключ сутності
     * @return знайдена сутність або null, якщо сутність не знайдена в БД
     */
    public Transportation findById(long id) {
        try {
            return entityManager.find(Transportation.class, id);
        } catch (NoResultException e) {
            //Перевезення не знайдено у базі даних. Повертаємо null
            return null;
        }
    }

    /**
     * Видалення перевезення з БД
     * @param transportation сутність для видалення
     */
    public void delete(Transportation transportation) {
        entityManager.remove(transportation);
    }

    /**
     * Видалення перевезення з БД за його первинним ключем
     * @param id первинний ключ перевезення, який необхідно видалити
     */
    public void delete(long id) {
        Transportation transportation = findById(id);
        delete(transportation);
    }

    /**
     * Метод зберігає сутність у БД - виконує INSERT sql, якщо первинний ключ не задано,
     * або UPDATE sql, якщо первинний ключ сутності задано.
     *
     * Якщо в методі відбувається вставка в БД, то первинний ключ сутності автоматично буде заповнений.
     *
     * @param transportation сутність для створення або оновлення
    @ return створена або збережена сутність
     */
    public Transportation save(Transportation transportation) {
        if (transportation.getTransportationId() == null)
            entityManager.persist(transportation);
        else
            entityManager.merge(transportation);
        return transportation;
    }

    /**
     * Оновити машину. Це приклад методу, який оновлює тільки окремий атрибут сутності.
     * @param id первинний ключ перевезення, чию машину потрібно оновити
     * @param car нова машина
     */
    public void updateCarId(long id, Car car) {
        Transportation transportation = findById(id);
        transportation.setCar(car);
    }

    /**
     * Пошук перевезення за датою.
     * @param date дата перевезення
     * @return знайдено перевезення або null, якщо перевезення не знайдено у БД
     */
    public Transportation findByDate(LocalDateTime date) {
        try {
            return entityManager.createQuery("SELECT t FROM Transportation t WHERE t.date = :date", Transportation.class)
                    .setParameter("date", date)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Перевезення не знайдено у базі даних. Повертаємо null
            return null;
        }
    }

    /**
     * Приклад методу, який повертає окремі значення з БД замість цілих сутностей.
     * Метод вибирає дату та машину за первинним ключем.
     * @param id первинний ключ перевезення
     * @return Map, де ключ - дата, значення - машина, або null, якщо дані не знайдені
     */
    public Map<String, String> findDateAndCar(long id) {
        try {
            Object[] row = (Object[]) entityManager.createQuery("SELECT t.date, t.car FROM Transportation t WHERE t.transportationId = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            LocalDate date = (LocalDate) row[0];
            Car car = (Car) row[1];

            Map<String, String> result = new HashMap<>();
            result.put("date", date.toString());
            result.put("car",car.toString());
            return result;
        } catch (NoResultException e) {
            // Дані не знайдено в базі даних. Повертаємо null
            return null;
        }
    }
}
