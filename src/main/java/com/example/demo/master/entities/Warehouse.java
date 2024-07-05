package com.example.demo.master.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "warehouse")
public class Warehouse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "warehouse_name")
    private String warehouseName;

    public Warehouse() {
        super();
    }

    @Builder
    public Warehouse(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg, String warehouseName) {
        super(createdAt, updatedAt, deletedFlg);
        this.warehouseName = warehouseName;
    }
}
