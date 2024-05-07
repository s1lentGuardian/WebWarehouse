package org.kharkiv.khpi.model.repository;

import java.util.HashMap;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.kharkiv.khpi.model.Car;

/**
 * DAO клас для роботи з таблицею car
 */
@Stateless
public class CarDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Пошук сутності за первинним ключем
     * @param id первинний ключ сутності
     * @return знайдена сутність або null, якщо сутність не знайдена в БД
     */
    public Car findById(long id) {
        try {
            return entityManager.find(Car.class, id);
        } catch (NoResultException e) {
            //Машина не знайдений у базі даних. Повертаємо null
            return null;
        }
    }

    /**
     * Видалення машини з БД
     * @param car сутність для видалення
     */
    public void delete(Car car) {
        entityManager.remove(car);
    }

    /**
     * Видалення машини з БД за його первинним ключем
     * @param id первинний ключ car, який необхідно видалити
     */
    public void delete(long id) {
        Car car = findById(id);
        delete(car);
    }

    /**
     * Метод зберігає сутність у БД - виконує INSERT sql, якщо первинний ключ не задано,
     * або UPDATE sql, якщо первинний ключ сутності задано.
     *
     * Якщо в методі відбувається вставка в БД, то первинний ключ сутності автоматично буде заповнений.
     *
     * @param car сутність для створення або оновлення
    @ return створена або збережена сутність
     */
    public Car save(Car car) {
        entityManager.persist(car);
        return car;
    }

    /**
     * Оновити вагу машини. Це приклад методу, який оновлює тільки окремий атрибут сутності.
     * @param id первинний ключ слона, чию вагу потрібно оновити
     * @param licensePlateNumber новий номер
     */
    public void updateLicensePlateNumber(long id, String licensePlateNumber) {
        Car car = findById(id);
        car.setLicensePlateNumber(licensePlateNumber);
    }

    /**
     * Пошук машини за маркою.
     * @param make марка машини
     * @return знайдена машина або null, якщо машина не знайдена у БД
     */
    public Car findByMake(String make) {
        try {
            return entityManager.createQuery("SELECT c FROM Car c WHERE c.make = :make", Car.class)
                    .setParameter("make", make)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Машина не знайдена у базі даних. Повертаємо null
            return null;
        }
    }

    /**
     * Приклад методу, який повертає окремі значення з БД замість цілих сутностей.
     * Метод вибирає марку та номер за первинним ключем.
     * @param id первинний ключ машини
     * @return Map, де ключ - марка, значення - номер, або null, якщо дані не знайдені
     */
    public Map<String, String> findMakeAndLicensePlateNumber(long id) {
        try {
            Object[] row = (Object[]) entityManager.createQuery("SELECT c.make, c.licensePlateNumber FROM Car c WHERE c.carId = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            String make = (String)row[0];
            String licensePlateNumber = (String)row[1];

            Map<String, String> result = new HashMap<>();
            result.put("make", make);
            result.put("licensePlateNumber", licensePlateNumber);
            return result;
        } catch (NoResultException e) {
            // Дані не знайдено в базі даних. Повертаємо null
            return null;
        }
    }


}
