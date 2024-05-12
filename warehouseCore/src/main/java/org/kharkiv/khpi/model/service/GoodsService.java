package org.kharkiv.khpi.model.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.kharkiv.khpi.model.Goods;
import org.kharkiv.khpi.model.Supplier;
import org.kharkiv.khpi.model.repository.GoodsDAO;
import org.kharkiv.khpi.model.repository.SupplierDAO;

import java.math.BigDecimal;
import java.util.List;

@Stateless
public class GoodsService {

    @Inject
    private GoodsDAO goodsDAO;

    @Inject
    private SupplierDAO supplierDAO;

    public List<Goods> findAllGoods() {
        return goodsDAO.findAllGoods();
    }

    public Goods save(Goods goods, Long supplierId) {
        Supplier supplier = supplierDAO.findById(supplierId);

        goods.setSupplier(supplier);

        return goodsDAO.save(goods);
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

    public void update(Long id, String typeOfGoods, String name,  String price, Long supplierId) {
        Supplier supplier = supplierDAO.findById(supplierId);

        Goods goods = goodsDAO.findById(id);

        goods.setTypeOfGoods(typeOfGoods);
        goods.setName(name);
        goods.setPrice(new BigDecimal(price));
        goods.setSupplier(supplier);

        goodsDAO.save(goods);
    }
}
