package com.example.demo.master.service;

import com.example.demo.constant.MessageCode;
import com.example.demo.event.Event;
import com.example.demo.exception.APIErrorDetail;
import com.example.demo.exception.CommonException;
import com.example.demo.master.dto.ProductDto;
import com.example.demo.master.entities.Product;
import com.example.demo.master.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductServiceImpl implements GenericService<Product>{

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper , MessageSource messageSource) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.messageSource = messageSource;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) throws CommonException{
        return productRepository.findAll(pageable);
    }

//    @EventListener(condition = "#event.eventType == T(com.example.demo.event.EventType).TYPE_PRODUCT")
    public void handleEvent(Event<?> event) {
        Map<Long ,String> messageMap = new HashMap<>();
        List<Long> productIdList  = (List<Long>) event.getData();

        for (Long aLong : productIdList) {
            Optional<Product> productOptional = findById(aLong);
            if (productOptional.isEmpty()) {
                messageMap.put(aLong, messageSource.getMessage(MessageCode.CHECK_EXISTS, null, Locale.getDefault()));
            }
        }
        if (messageMap.isEmpty()){
            event.getFuture().complete(null);
        }else {
            event.getFuture().complete(messageMap);
        }
    }

    @Override
    @Transactional(rollbackFor = {CommonException.class , Exception.class})
    public List<Product> save(List<Product> productList , Locale locale) throws CommonException{
        List<Product> createProductResult = new ArrayList<>();
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            boolean hasError = false;
            if (!CollectionUtils.isEmpty(productList)) {

                List<APIErrorDetail> errorDetails = new ArrayList<>();
                for (int i = 0; i < productList.size(); i++) {

                    ProductDto productDto = modelMapper.map(productList.get(i), ProductDto.class);
                    String productName = productDto.getProductName();
                    if (productName.length() > 20) {
                        APIErrorDetail errorDetail = new APIErrorDetail((long) i,
                                "productName",
                                MessageCode.CHECK_MAXLENGTH_PARAMETER,
                                messageSource.getMessage(
                                        MessageCode.CHECK_MAXLENGTH_PARAMETER,
                                        new Object[]{1, 20}, locale)
                        );
                        errorDetails.add(errorDetail);
                        hasError = true;
                    }
                }

                if (hasError) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.CHECK_MAXLENGTH_PARAMETER)
                            .setStatusCode(HttpStatus.BAD_REQUEST)
                            .setErrorDetails(errorDetails);
                }
                List<Product> listProduct = new ArrayList<>();
                for (Product p : productList) {
                    String productName = p.getProductName();
                    Product product = Product.builder()
                            .productName(productName)
                            .createdAt(currentTime)
                            .updatedAt(currentTime)
                            .deletedFlg(0)
                            .build();
                    listProduct.add(product);
                }
                createProductResult = productRepository.saveAll(listProduct);
            }
        } catch (CommonException e) {
            throw e;
        }
        catch (Exception e) {
            throw new CommonException(
                MessageCode.INTERNAL_ERROR,
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
                );
        }
        return createProductResult;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
