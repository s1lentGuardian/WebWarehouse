package org.kharkiv.khpi.model.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.kharkiv.khpi.model.Goods;
import org.kharkiv.khpi.model.Warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO клас для роботи з таблицею warehouse
 */
@Stateless
public class WarehouseDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Warehouse> findAllWarehouses() {
        try {
            TypedQuery<Warehouse> query = entityManager.createQuery("SELECT w FROM Warehouse w", Warehouse.class);
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Пошук сутності за первинним ключем
     * @param id первинний ключ сутності
     * @return знайдена сутність або null, якщо сутність не знайдена в БД
     */
    public Warehouse findById(long id) {
        try {
            return entityManager.find(Warehouse.class, id);
        } catch (NoResultException e) {
            //склад не знайдений у базі даних. Повертаємо null
            return null;
        }
    }

    /**
     * Видалення складу з БД
     * @param warehouse сутність для видалення
     */
    public void delete(Warehouse warehouse) {
        entityManager.remove(warehouse);
    }

    /**
     * Видалення складу з БД за його первинним ключем
     * @param id первинний ключ складу, який необхідно видалити
     */
    public void delete(long id) {
        Warehouse warehouse = findById(id);
        delete(warehouse);
    }

    /**
     * Метод зберігає сутність у БД - виконує INSERT sql, якщо первинний ключ не задано,
     * або UPDATE sql, якщо первинний ключ сутності задано.
     *
     * Якщо в методі відбувається вставка в БД, то первинний ключ сутності автоматично буде заповнений.
     *
     * @param warehouse сутність для створення або оновлення
    @ return створена або збережена сутність
     */
    public Warehouse save(Warehouse warehouse) {
//        Goods good = entityManager.find(Goods.class, 2L);
//        warehouse.addGoods(good);

        if (warehouse.getWarehouseId() == null)
            entityManager.persist(warehouse);
        else
            entityManager.merge(warehouse);
//        entityManager.persist(warehouse);

        return warehouse;
    }

    /**
     * Оновити кількість місць у складі. Це приклад методу, який оновлює тільки окремий атрибут сутності.
     * @param id первинний ключ складу, чию кількість місць потрібно оновити
     * @param numberPlaces нова кількість місць
     */
    public void updateNumberPlaces(long id, Long numberPlaces) {
        Warehouse warehouse = findById(id);
        warehouse.setNumberPlaces(numberPlaces);
    }

    /**
     * Пошук складу за назвою.
     * @param name ім'я складу
     * @return знайден склад або null, якщо склад не знайден у БД
     */
    public Warehouse findByName(String name) {
        try {
            return entityManager.createQuery("SELECT wh FROM Warehouse wh WHERE wh.name = :name", Warehouse.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Склад не знайдена у базі даних. Повертаємо null
            return null;
        }
    }

    /**
     * Отримання списку складів кількістю місць, більшою або рівним заданому.
     * @param numberPlaces мінімальна кількість місць у складах
     * @return список складів, які задовольняють умові
     */
    public List<Warehouse> findWarehousesWithMinNumberPlaces(Long numberPlaces) {
        return entityManager.createQuery("SELECT wh FROM Warehouse wh WHERE wh.numberPlaces >= :numberPlaces", Warehouse.class)
                .setParameter("numberPlaces", numberPlaces)
                .getResultList();
    }

    /**
     * Приклад методу, який повертає окремі значення з БД замість цілих сутностей.
     * Метод вибирає місто та адресу складу
     * @param id первинний ключ складу
     * @return Map, де ключ - місто, значення - адресу складу, або null, якщо дані не знайдені
     */
    public Map<String, String> findCityAndAddressLocation(long id) {
        try {
            Object[] row = (Object[]) entityManager.createQuery("SELECT wh.city, wh.addressLocation FROM Warehouse wh WHERE wh.warehouseId = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            String city = (String)row[0];
            String addressLocation = (String)row[1];

            Map<String, String> result = new HashMap<>();
            result.put("city", city);
            result.put("addressLocation", addressLocation);
            return result;
        } catch (NoResultException e) {
            // Дані не знайдено в базі даних. Повертаємо null
            return null;
        }
    }
}
