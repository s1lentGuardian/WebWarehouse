package org.kharkiv.khpi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    private Long warehouseId;

    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Number places cannot be empty")
    @Column(name = "number_places")
    private Long numberPlaces;

    @Column(name = "country")
    private String country;

    @NotEmpty(message = "City cannot be empty")
    @Column(name = "city")
    private String city;

    @NotEmpty(message = "Address Location cannot be empty")
    @Column(name = "address_location")
    private String addressLocation;

    @ManyToMany()
    @JoinTable(name = "warehouse_of_goods",
            joinColumns = @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id"),
            inverseJoinColumns = @JoinColumn(name = "goods_id", referencedColumnName = "goods_id"))
    private Set<Goods> goods = new HashSet<>();

    public Warehouse(String name, Long numberPlaces, String country, String city, String addressLocation) {
        this.name = name;
        this.numberPlaces = numberPlaces;
        this.country = country;
        this.city = city;
        this.addressLocation = addressLocation;
    }

    public Warehouse() {
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumberPlaces() {
        return numberPlaces;
    }

    public void setNumberPlaces(Long numberPlaces) {
        this.numberPlaces = numberPlaces;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLocation() {
        return addressLocation;
    }

    public void setAddressLocation(String addressLocation) {
        this.addressLocation = addressLocation;
    }

    public Set<Goods> getGoods() {
        return goods;
    }

    public void setGoods(Set<Goods> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "warehouseId=" + warehouseId +
                ", name='" + name + '\'' +
                ", numberPlaces=" + numberPlaces +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", addressLocation='" + addressLocation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(warehouseId, warehouse.warehouseId) && Objects.equals(name, warehouse.name) && Objects.equals(numberPlaces, warehouse.numberPlaces) && Objects.equals(country, warehouse.country) && Objects.equals(city, warehouse.city) && Objects.equals(addressLocation, warehouse.addressLocation) && Objects.equals(goods, warehouse.goods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseId, name, numberPlaces, country, city, addressLocation, goods);
    }

    public void addGoods(Goods good) {
        this.goods.add(good);
        good.getWarehouses().add(this);
    }
}
