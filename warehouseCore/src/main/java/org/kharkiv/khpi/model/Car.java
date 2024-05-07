package org.kharkiv.khpi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long carId;

    @Column(name = "make")
    private String make;

    @Column(name = "type_of_car")
    private String typeOfCar;

    @Column(name = "license_plate_number")
    private String licensePlateNumber;

    public Long getId() {
        return carId;
    }

    public void setId(Long id) {
        this.carId = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getTypeOfCar() {
        return typeOfCar;
    }

    public void setTypeOfCar(String typeOfCar) {
        this.typeOfCar = typeOfCar;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + carId +
                ", make='" + make + '\'' +
                ", typeOfCar='" + typeOfCar + '\'' +
                ", licensePlateNumber='" + licensePlateNumber + '\'' +
                '}';
    }
}
