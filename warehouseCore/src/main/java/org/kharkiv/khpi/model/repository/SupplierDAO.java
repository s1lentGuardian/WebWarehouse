package org.kharkiv.khpi.model.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.kharkiv.khpi.model.Supplier;

import java.util.HashMap;
import java.util.Map;

/**
 * DAO клас для роботи з таблицею supplier
 */
@Stateless
public class SupplierDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Пошук сутності за первинним ключем
     * @param id первинний ключ сутності
     * @return знайдена сутність або null, якщо сутність не знайдена в БД
     */
    public Supplier findById(long id) {
        try {
            return entityManager.find(Supplier.class, id);
        } catch (NoResultException e) {
            // Слон не знайдений у базі даних. Повертаємо null
            return null;
        }
    }

    /**
     * Видалення supplier з БД
     * @param supplier сутність для видалення
     */
    public void delete(Supplier supplier) {
        entityManager.remove(supplier);
    }

    /**
     * Видалення supplier з БД за його первинним ключем
     * @param id первинний ключ supplier, який необхідно видалити
     */
    public void delete(long id) {
        Supplier supplier = findById(id);
        delete(supplier);
    }

    /**
     * Метод зберігає сутність у БД - виконує INSERT sql, якщо первинний ключ не задано,
     * або UPDATE sql, якщо первинний ключ сутності задано.
     *
     * Якщо в методі відбувається вставка в БД, то первинний ключ сутності автоматично буде заповнений.
     *
     * @param supplier сутність для створення або оновлення
    @ return створена або збережена сутність
     */
    public Supplier save(Supplier supplier) {
        if(supplier.getSupplierId() == null)
            entityManager.persist(supplier);
        else
            entityManager.merge(supplier);

        return supplier;
    }

    /**
     * Оновити номер телефона supplier. Це приклад методу, який оновлює тільки окремий атрибут сутності.
     * @param id первинний ключ supplier, чий номер телефону потрібно оновити
     * @param phoneNumber новий номер телефону
     */
    public void updatePhoneNumber(long id, String phoneNumber) {
         Supplier supplier = findById(id);
        supplier.setPhoneNumber(phoneNumber);
    }

    /**
     * Пошук supplier за його номером телефона.
     * @param phoneNumber номер телефону
     * @return знайдений supplier або null, якщо supplier не знайдений у БД
     */
    public Supplier findByPhoneNumber(String phoneNumber) {
        try {
            return entityManager.createQuery("SELECT s FROM Supplier s WHERE s.phoneNumber = :phoneNumber", Supplier.class)
                    .setParameter("phoneNumber", phoneNumber)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Слон не знайдений у базі даних. Повертаємо null
            return null;
        }
    }


    /**
     * Приклад методу, який повертає окремі значення з БД замість цілих сутностей.
     * Метод вибирає місто supplier і номер телефону за первинним ключем.
     * @param id первинний ключ supplier
     * @return Map, де ключ - місто, значення - номер телефону, або null, якщо дані не знайдені
     */
    public Map<String, String> findCityAndPhoneNumber(long id) {
        try {
            Object[] row = (Object[]) entityManager.createQuery("SELECT s.city, s.phoneNumber FROM Supplier s WHERE s.supplierId = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            String city = (String)row[0];
            String phoneNumber = (String)row[1];

            Map<String, String> result = new HashMap<>();
            result.put("city", city);
            result.put("phoneNumber", phoneNumber);
            return result;
        } catch (NoResultException e) {
            // Дані не знайдено в базі даних. Повертаємо null
            return null;
        }
    }
}
