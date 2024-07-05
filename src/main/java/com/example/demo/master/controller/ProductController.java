package com.example.demo.master.controller;

import com.example.demo.exception.CommonException;
import com.example.demo.master.entities.Product;
import com.example.demo.master.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping(value = "/create-product")
    public ResponseEntity<List<Product>> createProduct(@RequestBody List<Product> productList , Locale locale) throws CommonException {
        return ResponseEntity.ok(productService.save(productList , locale));
    }

}
