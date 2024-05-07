package org.kharkiv.khpi.model;

import jakarta.persistence.*;

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

    @Column(name = "name")
    private String name;

    @Column(name = "number_places")
    private Long numberPlaces;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "address_location")
    private String addressLocation;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "warehouse_of_goods",
            joinColumns = @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id"),
            inverseJoinColumns = @JoinColumn(name = "goods_id", referencedColumnName = "goods_id"))
    private Set<Goods> goods = new HashSet<>();

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
