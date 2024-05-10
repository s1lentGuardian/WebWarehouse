package org.kharkiv.khpi.model.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.kharkiv.khpi.model.Car;
import org.kharkiv.khpi.model.Goods;
import org.kharkiv.khpi.model.Transportation;
import org.kharkiv.khpi.model.Warehouse;
import org.kharkiv.khpi.model.repository.CarDAO;
import org.kharkiv.khpi.model.repository.GoodsDAO;
import org.kharkiv.khpi.model.repository.TransportationDAO;
import org.kharkiv.khpi.model.repository.WarehouseDAO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Stateless
public class TransportationService {

    private static final String PATTERN = "yyyy-MM-dd";

    @Inject
    private TransportationDAO transportationDAO;

    @Inject
    private GoodsDAO goodsDAO;

    @Inject
    private CarDAO carDAO;

    @Inject
    private WarehouseDAO warehouseDAO;

    @Transactional
    public Transportation createTransportation(Long carId, Long goodsId, Integer count, Long pickUpFromWarehouseId, Long bringToWarehouseId, String dateStr) {
        Car car = carDAO.findById(carId);
        Goods goods = goodsDAO.findById(goodsId);
        Warehouse pickUpFromWarehouse = warehouseDAO.findById(pickUpFromWarehouseId);
        Warehouse bringToWarehouse = warehouseDAO.findById(bringToWarehouseId);

        Transportation transportation = new Transportation();
        transportation.setCar(car);
        transportation.addGoods(goods);
        transportation.setCount(count);
        transportation.setPickUpFromWarehouse(pickUpFromWarehouse);
        transportation.setBringToWarehouse(bringToWarehouse);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDate date = LocalDate.parse(dateStr, formatter);
        transportation.setDate(date);

        return transportationDAO.save(transportation);
    }

    public List<Transportation> findAllTransportations() {
        return transportationDAO.findAllTransportations();
    }

    public void delete(Long id) {
        transportationDAO.delete(id);
    }
}
