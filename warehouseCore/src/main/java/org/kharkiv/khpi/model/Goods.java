package org.kharkiv.khpi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Клас, що представляє сутність "Товари"
 * (таблицю 'goods' у базі даних).
 */

@Entity
@Table(name = "goods")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "type_of_goods")
    private String typeOfGoods;

    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Price cannot be empty")
    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne()
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToMany(mappedBy = "goods")
    private Set<Warehouse> warehouses = new HashSet<>();

    @ManyToMany(mappedBy = "goods")
    private Set<Transportation> transportations = new HashSet<>();

    public Goods() {
    }

    public Goods(String typeOfGoods, String name, BigDecimal price) {
        this.typeOfGoods = typeOfGoods;
        this.name = name;
        this.price = price;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getTypeOfGoods() {
        return typeOfGoods;
    }

    public void setTypeOfGoods(String typeOfGoods) {
        this.typeOfGoods = typeOfGoods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(Set<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public Set<Transportation> getTransportations() {
        return transportations;
    }

    public void setTransportations(Set<Transportation> transportations) {
        this.transportations = transportations;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", typeOfGoods='" + typeOfGoods + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", supplier=" + supplier +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return Objects.equals(goodsId, goods.goodsId) && Objects.equals(typeOfGoods, goods.typeOfGoods) && Objects.equals(name, goods.name) && Objects.equals(price, goods.price) && Objects.equals(supplier, goods.supplier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goodsId, typeOfGoods, name, price, supplier);
    }
}
