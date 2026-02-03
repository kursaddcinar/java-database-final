package com.project.code.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotNull;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id; // MongoDB için String ID

    @NotNull(message = "Müşteri boş olamaz")
    private Long customerId;

    @NotNull(message = "Ürün boş olamaz")
    private Long productId;

    @NotNull(message = "Mağaza boş olamaz")
    private Long storeId;

    @NotNull(message = "Puan boş olamaz")
    private Integer rating;

    private String comment;

    public Review(Long customerId, Long productId, Long storeId, Integer rating, String comment) {
        this.customerId = customerId;
        this.productId = productId;
        this.storeId = storeId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getter ve Setterlar
    // ...
}