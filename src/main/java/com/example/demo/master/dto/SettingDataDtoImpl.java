package com.example.demo.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SettingDataDtoImpl implements SettingDataDtos {

//    @JsonProperty(value = "id")
    private Long id ;

//    @JsonProperty(value = "screen_id")
    private Long screenId;

//    @JsonProperty(value = "column_name")
    private String columnName;

//    @JsonProperty(value = "table_name")
    private String tableName;

//    @JsonProperty(value = "data_type")
    private String dataType;

//    @JsonProperty(value = "column_width")
    private Integer columnWidth;

//    @JsonProperty(value = "status")
    private Integer status;

    private List<String> tableNameList;
}
