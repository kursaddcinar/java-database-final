package com.project.code.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference("inventory-product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonBackReference("inventory-store")
    private Store scaleStore; // Not: Store entity'sini de oluşturmuş olmalısın

    private Integer stockLevel;

    // Boş Constructor (JPA için gerekli)
    public Inventory() {}

    // Parametreli Constructor (Yönerge gereği)
    public Inventory(Product product, Store store, Integer stockLevel) {
        this.product = product;
        this.scaleStore = store;
        this.stockLevel = stockLevel;
    }

    // Getter ve Setter'lar
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Store getStore() { return scaleStore; }
    public void setStore(Store store) { this.scaleStore = store; }

    public Integer getStockLevel() { return stockLevel; }
    public void setStockLevel(Integer stockLevel) { this.stockLevel = stockLevel; }
}