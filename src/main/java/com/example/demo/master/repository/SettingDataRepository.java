package com.example.demo.master.repository;

import com.example.demo.master.entities.SettingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingDataRepository extends JpaRepository<SettingData, Long> {

    @Query(nativeQuery = true , value = "select g.id , g.screen_id , g.column_name , g.data_type , g.column_width" +
            " from general_data_table_search g where g.screen_id = :screenId order by g.id")
    List<SettingData> getListSettingData(@Param("screenId") Integer screenId);
}
