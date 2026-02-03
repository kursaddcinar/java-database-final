package com.project.code.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Mağaza ismi boş olamaz")
    @NotBlank(message = "Mağaza ismi boşluktan oluşamaz")
    private String name;

    @NotNull(message = "Adres boş olamaz")
    @NotBlank(message = "Adres boşluktan oluşamaz")
    private String address;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @JsonManagedReference("inventory-store")
    private List<Inventory> inventories;

    // Boş yapıcı
    public Store() {}

    // Parametreli yapıcı
    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Getter ve Setterlar
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<Inventory> getInventories() { return inventories; }
    public void setInventories(List<Inventory> inventories) { this.inventories = inventories; }
}