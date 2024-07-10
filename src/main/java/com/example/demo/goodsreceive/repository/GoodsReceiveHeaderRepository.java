package com.example.demo.goodsreceive.repository;

import com.example.demo.goodsreceive.entities.GoodsReceiveHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GoodsReceiveHeaderRepository extends JpaRepository<GoodsReceiveHeader , Long> {
    @Query(nativeQuery = true , value = "EXEC get_data_search @screen_id = :screenId")
    List<Map<String, Object>> getGoodsReceiveList(@Param("screenId") String screenId);

}
