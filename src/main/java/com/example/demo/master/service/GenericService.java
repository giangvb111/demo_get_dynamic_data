package com.example.demo.master.service;

import com.example.demo.exception.CommonException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface GenericService<T> {
    Page<T> findAll(Pageable pageable) throws CommonException;
    List<T> save(List<T> t , Locale locale) throws CommonException;
    Optional<T> findById(Long id);
    void deleteById(Long id);
}
