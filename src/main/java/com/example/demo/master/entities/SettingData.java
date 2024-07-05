package com.example.demo.master.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "general_data_table_search")
public class SettingData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id ;

    @Column(name = "screen_id")
    private Long screenId;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "column_width")
    private Integer columnWidth;

}
