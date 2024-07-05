package com.example.demo.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @JsonProperty(value = "product_id")
    private Long productId;

    @JsonProperty(value = "product_name")
    private String productName;

    @JsonProperty(value = "created_at")
    private String createdAt;

    @JsonProperty(value = "updated_at")
    private String updatedAt;

    @JsonProperty(value = "deleted_flg")
    private int deletedFlg;
}
