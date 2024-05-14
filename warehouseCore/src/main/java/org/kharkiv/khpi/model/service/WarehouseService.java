package org.kharkiv.khpi.model.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;
import org.kharkiv.khpi.model.Goods;
import org.kharkiv.khpi.model.Warehouse;
import org.kharkiv.khpi.model.repository.GoodsDAO;
import org.kharkiv.khpi.model.repository.SupplierDAO;
import org.kharkiv.khpi.model.repository.WarehouseDAO;

import java.math.BigDecimal;
import java.util.List;

@Stateless
public class WarehouseService {

    @Inject
    private GoodsDAO goodsDAO;

    @Inject
    private WarehouseDAO warehouseDAO;

    public List<Warehouse> findAllWarehouses() {
        return warehouseDAO.findAllWarehouses();
    }

    public Warehouse save(Warehouse warehouse, String[] goodsIds) {
        for (String goodsId : goodsIds) {
            Goods goods = goodsDAO.findById(Long.parseLong(goodsId));
            warehouse.addGoods(goods);
        }

        return warehouseDAO.save(warehouse);
    }

    private static Warehouse createWarehouse(String name, Long numberPlace, String country, String city, String addressLocation, Goods goods) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(name);
        warehouse.setNumberPlaces(numberPlace);
        warehouse.setCountry(country);
        warehouse.setCity(city);
        warehouse.setAddressLocation(addressLocation);
        warehouse.addGoods(goods);

        return warehouse;
    }

    public void delete(Long id) {
        warehouseDAO.delete(id);
    }

    public void update(Long id, String name, Long numberPlace, String country, String city, String addressLocation, Long goodId) {
        Warehouse warehouse = warehouseDAO.findById(id);

        Goods goods = goodsDAO.findById(goodId);
        warehouse.setName(name);
        warehouse.setNumberPlaces(numberPlace);
        warehouse.setCountry(country);
        warehouse.setCity(city);
        warehouse.setAddressLocation(addressLocation);
        warehouse.addGoods(goods);

        warehouseDAO.save(warehouse);
    }
}
