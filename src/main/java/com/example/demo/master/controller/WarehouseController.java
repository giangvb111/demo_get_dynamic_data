package com.example.demo.master.controller;

import com.example.demo.exception.CommonException;
import com.example.demo.master.entities.Product;
import com.example.demo.master.entities.Warehouse;
import com.example.demo.master.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping(value = "/create-warehouse")
    public ResponseEntity<List<Warehouse>> createProduct(@RequestBody List<Warehouse> warehouseList , Locale locale) throws CommonException {
        return ResponseEntity.ok(warehouseService.save(warehouseList , locale));
    }
}
