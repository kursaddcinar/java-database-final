package com.project.code.Model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "İsim boş olamaz")
    private String name;

    @NotNull(message = "Email boş olamaz")
    private String email;

    @NotNull(message = "Telefon boş olamaz")
    private String phone;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderDetails> orders; // OrderDetails modelini bir sonraki adımda detaylandıracağız

    // Getter ve Setter'lar
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<OrderDetails> getOrders() { return orders; }
    public void setOrders(List<OrderDetails> orders) { this.orders = orders; }
}