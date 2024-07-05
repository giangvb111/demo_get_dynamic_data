package com.example.demo.master.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Setter
@Getter
@Table(name = "product")
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name" , nullable = false)
    private String productName;

    public Product() {
        super();
    }

    @Builder
    public Product(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg, String productName) {
        super(createdAt,updatedAt,deletedFlg);
        this.productName = productName;
    }
}
