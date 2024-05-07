package org.kharkiv.khpi.model.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;
import org.kharkiv.khpi.model.Car;
import org.kharkiv.khpi.model.Goods;
import org.kharkiv.khpi.model.Transportation;
import org.kharkiv.khpi.model.Warehouse;
import org.kharkiv.khpi.model.repository.CarDAO;
import org.kharkiv.khpi.model.repository.GoodsDAO;
import org.kharkiv.khpi.model.repository.TransportationDAO;
import org.kharkiv.khpi.model.repository.WarehouseDAO;

import java.time.LocalDate;

@Stateless
public class TransportationService {

    @Inject
    private TransportationDAO transportationDAO;

    @Inject
    private GoodsDAO goodsDAO;

    @Inject
    private CarDAO carDAO;

    @Inject
    private WarehouseDAO warehouseDAO;

    @Transactional
    public Transportation createTransportation() {
        Car car = carDAO.findById(1L);
        Goods goods = goodsDAO.findById(1L);
        Warehouse pickUpFromWarehouse = warehouseDAO.findById(1L);
        Warehouse bringToWarehouse = warehouseDAO.findById(2L);

        Transportation transportation = new Transportation();
        transportation.setCar(car);
        transportation.addGoods(goods);
        transportation.setPickUpFromWarehouse(pickUpFromWarehouse);
        transportation.setBringToWarehouse(bringToWarehouse);
        transportation.setDate(LocalDate.of(2024, 9, 23));

        return transportationDAO.save(transportation);
    }

}
