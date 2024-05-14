package org.kharkiv.khpi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "car")
public class Car {

    private static final String LICENSE_PLATE_PATTERN = "^[A-Za-z]{2}\\d{4}[A-Za-z]{2}$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long carId;

    @NotEmpty(message = "Make cannot be empty")
    @Column(name = "make")
    private String make;

    @Column(name = "type_of_car")
    private String typeOfCar;

    @NotEmpty(message = "Licence plate number cannot be empty")
    @Pattern(regexp = LICENSE_PLATE_PATTERN, message = "License plate number must be in form of AX0000CE")
    @Column(name = "license_plate_number")
    private String licensePlateNumber;

    public Car() {
    }

    public Car(String make, String typeOfCar, String licensePlateNumber) {
        this.make = make;
        this.typeOfCar = typeOfCar;
        this.licensePlateNumber = licensePlateNumber;
    }

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
