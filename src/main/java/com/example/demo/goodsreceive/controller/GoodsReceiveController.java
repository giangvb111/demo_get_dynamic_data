package com.example.demo.goodsreceive.controller;

import com.example.demo.exception.CommonException;
import com.example.demo.goodsreceive.dto.GoodsReceiveDto;
import com.example.demo.goodsreceive.service.GoodsReceiveHeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("goods-receive")
@RequiredArgsConstructor
public class GoodsReceiveController {

    private final GoodsReceiveHeaderService goodsReceiveHeaderService;

    @PostMapping(value = "/create")
    public ResponseEntity<GoodsReceiveDto> createGoodsReceive(@RequestBody GoodsReceiveDto goodsReceiveDto , Locale locale) throws CommonException {
        return ResponseEntity.ok(goodsReceiveHeaderService.createGoodsReceive(goodsReceiveDto ,locale));
    }

    @GetMapping(value = "/get-list")
    public ResponseEntity<List<Map<String , Object>>> getListGoodsReceive(@RequestParam(value = "screenId") String screenId) throws CommonException{
        return ResponseEntity.ok(goodsReceiveHeaderService.getListGoodsReceive(screenId));
    }


}