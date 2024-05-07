package org.kharkiv.khpi.model.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.kharkiv.khpi.model.Goods;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO клас для роботи з таблицею goods
 */
@Stateless
public class GoodsDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Пошук сутності за первинним ключем
     * @param id первинний ключ сутності
     * @return знайдена сутність або null, якщо сутність не знайдена в БД
     */
    public Goods findById(long id) {
        try {
            return entityManager.find(Goods.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Видалення goods з БД
     * @param goods сутність для видалення
     */
    public void delete(Goods goods) {
        entityManager.remove(goods);
    }

    /**
     * Видалення goods з БД за його первинним ключем
     * @param id первинний ключ goods, який необхідно видалити
     */
    public void delete(long id) {
        Goods goods = findById(id);
        delete(goods);
    }

    /**
     * Метод зберігає сутність у БД - виконує INSERT sql, якщо первинний ключ не задано,
     * або UPDATE sql, якщо первинний ключ сутності задано.
     *
     * Якщо в методі відбувається вставка в БД, то первинний ключ сутності автоматично буде заповнений.
     *
     * @param goods сутність для створення або оновлення
    @ return створена або збережена сутність
     */
    public Goods save(Goods goods) {
        entityManager.persist(goods);
        return goods;
    }

    /**
     * Оновити вагу goods. Це приклад методу, який оновлює тільки окремий атрибут сутності.
     * @param id первинний ключ товару, чий name потрібно оновити
     * @param name нова назва
     */
    public void updateName(long id, String name) {
        Goods goods = findById(id);
        goods.setName(name);
    }

    /**
     * Пошук товару за його назвою.
     * @param name назва товару
     * @return знайдений товар або null, якщо товар не знайдений у БД
     */
    public Goods findByName(String name) {
        try {
            return entityManager.createQuery("SELECT g FROM Goods g WHERE g.name = :name", Goods.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Слон не знайдений у базі даних. Повертаємо null
            return null;
        }
    }

    /**
     * Отримання списку goods із ціною, більшою або рівною заданій.
     * @param price мінімальна ціна
     * @return список товарів, які задовольняють умові
     */
    public List<Goods> findGoodsWithMinPrice(BigDecimal price) {
        return entityManager.createQuery("SELECT g FROM Goods g WHERE g.price >= :price", Goods.class)
                .setParameter("price", price)
                .getResultList();
    }

    /**
     * Приклад методу, який повертає окремі значення з БД замість цілих сутностей.
     * Метод вибирає назву товару і ціну товару за первинним ключем.
     * @param id первинний ключ товару
     * @return Map, де ключ - назва товару, значення - ціна на товар, або null, якщо дані не знайдені
     */
    public Map<String, String> findNameAndPrice(long id) {
        try {
            Object[] row = (Object[]) entityManager.createQuery("SELECT g.name, g.price FROM Goods g WHERE g.goodsId = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            String name = (String)row[0];
            BigDecimal price = (BigDecimal)row[1];

            Map<String, String> result = new HashMap<>();
            result.put("name", name);
            result.put("price", price.toString());
            return result;
        } catch (NoResultException e) {
            // Дані не знайдено в базі даних. Повертаємо null
            return null;
        }
    }

}
