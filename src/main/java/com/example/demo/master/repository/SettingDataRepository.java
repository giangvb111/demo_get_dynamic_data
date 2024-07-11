package com.example.demo.master.repository;

import com.example.demo.master.dto.SettingDataDtos;
import com.example.demo.master.entities.SettingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SettingDataRepository extends JpaRepository<SettingData, Long> {

    @Query(nativeQuery = true , value = "select g.* " +
            " from setting_data g where g.screen_id = :screenId order by g.id")
    List<SettingDataDtos> getListSettingData(@Param("screenId") Integer screenId);

    @Query(nativeQuery = true , value = "SELECT COLUMN_NAME \n" +
            "FROM INFORMATION_SCHEMA.COLUMNS \n" +
            "WHERE TABLE_NAME = :tableName")
    List<String> listColumnName(@Param("tableName") String tableName);

    @Query(nativeQuery = true , value = "SELECT sd.* FROM setting_data sd WHERE sd.id IN :settingIdList")
    List<SettingData> findSettingDataByIds(List<Long> settingIdList);

    @Query(nativeQuery = true , value = "select gts.table_name from general_table_setting gts where gts.screen_id  = :screenId")
    List<String> listTableName (Integer screenId);

    @Query(nativeQuery = true , value = "select gms.reference_screen_id ,gms.reference_screen_name from general_system_master gms where gms.screen_id  = :screenId")
    List<Map<String , String>> screenOptionList (Integer screenId);

    @Query(nativeQuery = true , value = "select m.screen_id from menu m where m.url = :url ")
    Integer getScreenIdByUrl(@Param("url") String url);
}
