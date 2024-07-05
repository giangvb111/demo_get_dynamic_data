package com.example.demo.master.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "warehouse")
public class Warehouse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "warehouse_name")
    private String warehouseName;

    public Warehouse() {
    }

    public Warehouse(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg) {
        super(createdAt, updatedAt, deletedFlg);
    }

    public Warehouse(Long warehouseId, String warehouseName) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
    }

    @Builder
    public Warehouse(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg, Long warehouseId, String warehouseName) {
        super(createdAt, updatedAt, deletedFlg);
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
    }
}
