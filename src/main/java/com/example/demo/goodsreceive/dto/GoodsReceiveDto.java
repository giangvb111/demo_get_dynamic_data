package com.example.demo.goodsreceive.dto;

import com.example.demo.goodsreceive.entities.GoodsReceiveDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsReceiveDto {

//    @JsonProperty(value = "goods_receive_header_id")
    private Long goodsReceiveHeaderId;

//    @JsonProperty(value = "goods_receive_header_no")
    private String goodsReceiveHeaderNo;

    private List<GoodsReceiveDetail> goodsReceiveDetailList;

}
