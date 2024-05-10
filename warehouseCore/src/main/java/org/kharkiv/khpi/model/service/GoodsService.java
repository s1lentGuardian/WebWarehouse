package org.kharkiv.khpi.model.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.kharkiv.khpi.model.Goods;
import org.kharkiv.khpi.model.Supplier;
import org.kharkiv.khpi.model.repository.GoodsDAO;

import java.math.BigDecimal;
import java.util.List;

@Stateless
public class GoodsService {

    @Inject
    private GoodsDAO goodsDAO;

    public List<Goods> findAllGoods() {
        return goodsDAO.findAllGoods();
    }

    public Goods save(String typeOfGoods, String name,  String price, String country, String city, String phoneNumber) {
        Supplier supplier = createSupplier(country, city, phoneNumber);
        Goods goods = createGoods(typeOfGoods, name, price);

        goods.setSupplier(supplier);

        return goodsDAO.save(goods);
    }

    public static Supplier createSupplier(String country, String city, String phoneNumber) {
        Supplier supplier = new Supplier();
        supplier.setCountry(country);
        supplier.setCity(city);
        supplier.setPhoneNumber(phoneNumber);

        return supplier;
    }

    private static Goods createGoods(String typeOfGoods, String name,  String price) {
        Goods goods = new Goods();
        goods.setTypeOfGoods(typeOfGoods);
        goods.setName(name);
        goods.setPrice(new BigDecimal(price));

        return goods;
    }

    public void delete(Long id) {
        goodsDAO.delete(id);
    }
}
