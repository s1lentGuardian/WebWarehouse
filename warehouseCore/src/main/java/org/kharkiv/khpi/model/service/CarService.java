package org.kharkiv.khpi.model.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.kharkiv.khpi.model.Car;
import org.kharkiv.khpi.model.repository.CarDAO;

import java.util.List;

@Stateless
public class CarService {

    @Inject
    private CarDAO carDAO;

    public List<Car> findAllCars() {
        return carDAO.findAllCars();
    }

    public Car save(Car car) {
        return carDAO.save(car);
    }

    private static Car createCar(String make, String type, String plate) {
        Car car = new Car();
        car.setMake(make);
        car.setTypeOfCar(type);
        car.setLicensePlateNumber(plate);

        return car;
    }

    public void delete(Long id) {
        carDAO.delete(id);
    }

    public void update(Long id, String make, String type, String plate) {
        Car car = carDAO.findById(id);

        car.setMake(make);
        car.setTypeOfCar(type);
        car.setLicensePlateNumber(plate);

        carDAO.save(car);
    }

}
