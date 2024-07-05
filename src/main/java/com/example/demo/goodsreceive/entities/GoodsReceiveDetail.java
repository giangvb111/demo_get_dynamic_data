package com.example.demo.goodsreceive.entities;

import com.example.demo.master.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "goods_receive_detail")
public class GoodsReceiveDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_receive_detail_id")
    private Long goodsReceiveDetailId;

    @Column(name = "goods_receive_detail_no")
    private String goodsReceiveDetailNo;

    @Column(name = "goods_receive_header_id")
    private Long goodsReceiveHeaderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private BigDecimal quantity;

    public GoodsReceiveDetail() {
    }

    @Builder
    public GoodsReceiveDetail(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg,
                              Long goodsReceiveDetailId, String goodsReceiveDetailNo, Long goodsReceiveHeaderId, Long productId , BigDecimal quantity) {
        super(createdAt, updatedAt, deletedFlg);
        this.goodsReceiveDetailId = goodsReceiveDetailId;
        this.goodsReceiveDetailNo = goodsReceiveDetailNo;
        this.goodsReceiveHeaderId = goodsReceiveHeaderId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
