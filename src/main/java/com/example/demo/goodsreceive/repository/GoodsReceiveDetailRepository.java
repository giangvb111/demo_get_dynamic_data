package com.example.demo.goodsreceive.repository;

import com.example.demo.goodsreceive.entities.GoodsReceiveDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsReceiveDetailRepository extends JpaRepository<GoodsReceiveDetail , Long> {
}
