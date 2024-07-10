package com.example.demo.master.repository;

import com.example.demo.master.entities.SettingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingDataRepository extends JpaRepository<SettingData, Long> {

    @Query(nativeQuery = true , value = "select g.*" +
            " from setting_data g where g.screen_id = :screenId order by g.id")
    List<SettingData> getListSettingData(@Param("screenId") Integer screenId);

    @Query(nativeQuery = true , value = "SELECT COLUMN_NAME \n" +
            "FROM INFORMATION_SCHEMA.COLUMNS \n" +
            "WHERE TABLE_NAME = :tableName")
    List<String> listColumnName(@Param("tableName") String tableName);

    @Query(nativeQuery = true , value = "SELECT sd.* FROM setting_data sd WHERE sd.id IN :settingIdList")
    List<SettingData> findSettingDataByIds(List<Long> settingIdList);
}
