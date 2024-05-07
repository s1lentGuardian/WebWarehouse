package org.kharkiv.khpi.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Клас, що представляє сутність "Перевезення"
 * (таблицю 'transportation' у базі даних).
 */
@Entity
@Table(name = "transportation")
public class Transportation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transportation_id")
    private Long transportationId;

    @OneToOne
    @JoinColumn(name = "car_id")
    public Car car;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "transportation_goods",
            joinColumns = @JoinColumn(name = "transportation_id", referencedColumnName = "transportation_id"),
            inverseJoinColumns = @JoinColumn(name = "goods_id", referencedColumnName = "goods_id"))
    private Set<Goods> goods = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "pick_up_from_warehouse_id")
    private Warehouse pickUpFromWarehouse;

    @ManyToOne
    @JoinColumn(name = "bring_to_warehouse_id")
    private Warehouse bringToWarehouse;

    @Column(name = "date")
    private LocalDate date;

    public Long getTransportationId() {
        return transportationId;
    }

    public void setTransportationId(Long transportationId) {
        this.transportationId = transportationId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Set<Goods> getGoods() {
        return goods;
    }

    public void setGoods(Set<Goods> goods) {
        this.goods = goods;
    }

    public Warehouse getPickUpFromWarehouse() {
        return pickUpFromWarehouse;
    }

    public void setPickUpFromWarehouse(Warehouse pickUpFromWarehouse) {
        this.pickUpFromWarehouse = pickUpFromWarehouse;
    }

    public Warehouse getBringToWarehouse() {
        return bringToWarehouse;
    }

    public void setBringToWarehouse(Warehouse bringToWarehouse) {
        this.bringToWarehouse = bringToWarehouse;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transportation{" +
                "transportationId=" + transportationId +
                ", car=" + car +
                ", goods=" + goods +
                ", pickUpFromWarehouse=" + pickUpFromWarehouse +
                ", bringToWarehouse=" + bringToWarehouse +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transportation that = (Transportation) o;
        return Objects.equals(transportationId, that.transportationId) && Objects.equals(car, that.car) && Objects.equals(goods, that.goods) && Objects.equals(pickUpFromWarehouse, that.pickUpFromWarehouse) && Objects.equals(bringToWarehouse, that.bringToWarehouse) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transportationId, car, goods, pickUpFromWarehouse, bringToWarehouse, date);
    }

    public void addGoods(Goods good) {
        this.goods.add(good);
        good.getTransportations().add(this);
    }
}
