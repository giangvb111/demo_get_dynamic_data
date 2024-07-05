package com.example.demo.goodsreceive.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingDataDto {

    @JsonProperty(value = "id")
    private Long id ;
    @JsonProperty(value = "screen_id")
    private Long screenId ;
    @JsonProperty(value = "column_name")
    private String columnName;
    @JsonProperty(value = "data_type")
    private String dataType;
    @JsonProperty(value = "column_width")
    private Integer columnWidth;
}
