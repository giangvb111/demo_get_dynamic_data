package com.example.demo.master.service;

import com.example.demo.constant.MessageCode;
import com.example.demo.event.Event;
import com.example.demo.exception.APIErrorDetail;
import com.example.demo.exception.CommonException;
import com.example.demo.master.entities.Warehouse;
import com.example.demo.master.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class WarehouseService implements GenericService<Warehouse> {
    
    private final WarehouseRepository warehouseRepository;

    private final MessageSource messageSource;
    @Override
    public Page<Warehouse> findAll(Pageable pageable) throws CommonException {
        return null;
    }

    @Override
    public List<Warehouse> save(List<Warehouse> warehouseList, Locale locale) throws CommonException {
            List<Warehouse> createWarehouseList = new ArrayList<>();
        try {

            LocalDateTime currentTime  = LocalDateTime.now();
            if (!CollectionUtils.isEmpty(warehouseList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                warehouseList.forEach(w -> {
                    if (StringUtils.hasLength(w.getWarehouseName())) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.longValue(),
                                "warehouseName",
                                MessageCode.CHECK_MAXLENGTH_PARAMETER,
                                messageSource.getMessage(MessageCode.CHECK_MAXLENGTH_PARAMETER, null, locale)
                        );
                        errorDetails.add(apiErrorDetail);
                    }
                    i.getAndIncrement();
                });

                if (!CollectionUtils.isEmpty(errorDetails)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.CHECK_MAXLENGTH_PARAMETER)
                            .setStatusCode(HttpStatus.BAD_REQUEST)
                            .setErrorDetails(errorDetails);
                }
                List<Warehouse> list = new ArrayList<>();
                for (Warehouse w: warehouseList) {
                    Warehouse warehouse = Warehouse.builder()
                            .warehouseName(w.getWarehouseName())
                            .createdAt(currentTime)
                            .updatedAt(currentTime)
                            .deletedFlg(0)
                            .build();
                    list.add(warehouse);
                }
                createWarehouseList = warehouseRepository.saveAllAndFlush(list);

            }
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(
                 MessageCode.INTERNAL_ERROR,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );

        }
        return createWarehouseList;
    }

    @Override
    public Optional<Warehouse> findById(Long id) {
        return warehouseRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {

    }

    @SuppressWarnings("unchecked")
    public void handleEventWarehouse(Event<?> event) {

        Map<Long ,String> messageMap = new HashMap<>();
        List<Long> warehouseId  = (List<Long>) event.getData();

        for (Long aLong: warehouseId) {
            Optional<Warehouse> warehouseOptional = findById(aLong);
            if (warehouseOptional.isEmpty()) {
                messageMap.put(aLong ,messageSource.getMessage(MessageCode.CHECK_EXISTS , null , Locale.getDefault()));
            }
        }
        if (messageMap.isEmpty()) {
            event.getFuture().complete(null);
        } else {
            event.getFuture().complete(messageMap);
        }
    }
}
