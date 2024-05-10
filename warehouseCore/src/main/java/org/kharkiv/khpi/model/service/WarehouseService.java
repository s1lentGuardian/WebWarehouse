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

    @Transactional
    public Warehouse createWarehouse() {
        Faker faker = new Faker();

        Goods goods = goodsDAO.findById(2L);

        Warehouse warehouse = new Warehouse();
//        Warehouse warehouse = warehouseDAO.findById(1);
        warehouse.setName(faker.name().name());
        warehouse.setNumberPlaces(faker.number().randomNumber());
        warehouse.setCountry(faker.country().name());
        warehouse.setCity(faker.address().city());
        warehouse.setAddressLocation(faker.address().fullAddress());
        warehouse.addGoods(goods);

//        goods = new Goods();
//        goods.setSupplier(supplierDAO.findById(2));
//        goods.setName(faker.name().name());
//        goods.setTypeOfGoods(faker.name().name());
//        goods.setPrice(BigDecimal.valueOf(faker.number().positive()));
//        warehouse.addGoods(goods);

        return warehouseDAO.save(warehouse);
    }

    public List<Warehouse> findAllWarehouses() {
        return warehouseDAO.findAllWarehouses();
    }

    public Warehouse save(String name, Long numberPlace, String country, String city, String addressLocation, Long goodsId) {
        Goods goods = goodsDAO.findById(goodsId);
        Warehouse warehouse = createWarehouse(name, numberPlace, country, city, addressLocation, goods);

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
}
