package com.example.demo.goodsreceive.service;

import com.example.demo.constant.MessageCode;
import com.example.demo.event.Event;
import com.example.demo.event.EventPublisher;
import com.example.demo.event.EventType;
import com.example.demo.exception.APIErrorDetail;
import com.example.demo.exception.CommonException;
import com.example.demo.goodsreceive.dto.GoodsReceiveDto;
import com.example.demo.goodsreceive.entities.GoodsReceiveDetail;
import com.example.demo.goodsreceive.repository.GoodsReceiveDetailRepository;
import com.example.demo.master.entities.Product;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class GoodsReceiveDetailService implements GenericService<GoodsReceiveDetail>{

    private final GoodsReceiveDetailRepository goodsReceiveDetailRepository;

    private final MessageSource messageSource;

    private final EventPublisher eventPublisher;

    @Override
    public Page<GoodsReceiveDetail> findAll(Pageable pageable) throws CommonException {
        return null;
    }

    @Override
    public List<GoodsReceiveDetail> save(List<GoodsReceiveDetail> t, Locale locale) throws CommonException {
        return null;
    }

    @Override
    public Optional<GoodsReceiveDetail> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    public  List<APIErrorDetail> checkGoodsReceiveDetail(@NonNull GoodsReceiveDto goodsReceiveDto ,  Locale locale) throws ExecutionException,
            InterruptedException, CommonException {

        List<APIErrorDetail> errorDetails = new ArrayList<>();
        CompletableFuture<Map<Long , String>> future;
        boolean hasError = false ;
        List<GoodsReceiveDetail> goodsReceiveDetailList = goodsReceiveDto.getGoodsReceiveDetailList();

        if (CollectionUtils.isEmpty(goodsReceiveDetailList)) {
            List<Long> warehouseListId = goodsReceiveDetailList.stream().map(GoodsReceiveDetail::getWarehouseId).toList();
            future = eventPublisher.publishEvent(EventType.TYPE_WAREHOUSE , null , warehouseListId);

            if (!CollectionUtils.isEmpty(future.get())) {
                future.get().forEach((key , value) -> {
                    APIErrorDetail errorDetail = new APIErrorDetail(key,
                            "warehouseId",
                            MessageCode.CHECK_EXISTS,
                            messageSource.getMessage(
                                    MessageCode.CHECK_EXISTS,
                                    null, locale)
                    );
                    errorDetails.add(errorDetail);
                });
            }

            List<Long> productListId = goodsReceiveDetailList.stream().map(GoodsReceiveDetail::getProductId).toList();
            future = eventPublisher.publishEvent(EventType.TYPE_PRODUCT , null , productListId);
            if (!CollectionUtils.isEmpty(future.get())) {
                future.get().forEach((key , value) ->{
                    APIErrorDetail apiErrorDetail = new APIErrorDetail(
                            key ,
                            "productId" ,
                            MessageCode.CHECK_EXISTS ,
                            messageSource.getMessage(MessageCode.CHECK_EXISTS ,null ,locale)
                    );
                    errorDetails.add(apiErrorDetail);
                });
            }

        }
        return errorDetails;
    }

}
