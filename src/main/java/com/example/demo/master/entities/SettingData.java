package com.example.demo.master.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "setting_data")
public class SettingData extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id ;

    @Column(name = "screen_id")
    private Long screenId;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "column_width")
    private Integer columnWidth;

    @Column(name = "status")
    private Integer status;

    public SettingData() {
        super();
    }

    @Builder
    public SettingData(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg ,Long id ,
                       Long screenId, String columnName, String tableName, String dataType, Integer columnWidth, Integer status) {
        super(createdAt , updatedAt , deletedFlg);
        this.id = id;
        this.screenId = screenId;
        this.columnName = columnName;
        this.tableName = tableName;
        this.dataType = dataType;
        this.columnWidth = columnWidth;
        this.status = status;
    }
}
