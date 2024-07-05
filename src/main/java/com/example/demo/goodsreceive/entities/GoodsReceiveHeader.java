package com.example.demo.goodsreceive.entities;

import com.example.demo.master.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "goods_receive_header")
public class GoodsReceiveHeader extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_receive_header_id")
    private Long goodsReceiveHeaderId;

    @Column(name = "goods_receive_header_no")
    private String goodsReceiveHeaderNo;

    public GoodsReceiveHeader() {
    }

    @Builder
    public GoodsReceiveHeader(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg, Long goodsReceiveHeaderId, String goodsReceiveHeaderNo) {
        super(createdAt, updatedAt, deletedFlg);
        this.goodsReceiveHeaderId = goodsReceiveHeaderId;
        this.goodsReceiveHeaderNo = goodsReceiveHeaderNo;
    }
}
