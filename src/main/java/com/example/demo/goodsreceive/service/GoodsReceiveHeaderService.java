package com.example.demo.goodsreceive.service;

import com.example.demo.constant.MessageCode;
import com.example.demo.exception.APIErrorDetail;
import com.example.demo.exception.CommonException;
import com.example.demo.goodsreceive.dto.GoodsReceiveDto;
import com.example.demo.goodsreceive.entities.GoodsReceiveDetail;
import com.example.demo.goodsreceive.entities.GoodsReceiveHeader;
import com.example.demo.goodsreceive.repository.GoodsReceiveDetailRepository;
import com.example.demo.goodsreceive.repository.GoodsReceiveHeaderRepository;
import com.example.demo.master.dto.SettingDataDtoImpl;
import com.example.demo.master.dto.SettingDataDtos;
import com.example.demo.master.service.SettingDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodsReceiveHeaderService implements GenericService<GoodsReceiveHeader>{

    private final GoodsReceiveHeaderRepository goodsReceiveHeaderRepository;

    private final GoodsReceiveDetailRepository goodsReceiveDetailRepository;

    private final GoodsReceiveDetailService goodsReceiveDetailService;

    private final SettingDataService settingDataService;

    @Override
    public Page<GoodsReceiveHeader> findAll(Pageable pageable) throws CommonException {
         pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return goodsReceiveHeaderRepository.findAll(pageable);
    }

    // Get List Data Setting For Screen
    private List<SettingDataDtoImpl> getListSettingData(Integer screenId) {
        return settingDataService.getSettingDataByScreenId(screenId);
    }

    /**
     *
     * @param screenId
     * @return
     * @throws CommonException
     */
    public List<Map<String , Object>> getListGoodsReceive(String screenId) throws CommonException {
        List<Map<String , Object>> resultList = new ArrayList<>();
        List<Map<String , Object>> dataMap = goodsReceiveHeaderRepository.getGoodsReceiveList(screenId);
        List<SettingDataDtoImpl> settingDataList = getListSettingData(Integer.parseInt(screenId));

        Set<String> settingDataKeys = settingDataList.stream()
                .map(SettingDataDtos::getColumnName)
                .map(key -> {
                    int indexOfDot = key.indexOf('.');
                    return indexOfDot != -1 ? key.substring(indexOfDot + 1) : key;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));

        for (Map<String, Object> map : dataMap) {
            Map<String, Object> resultMap = new LinkedHashMap<>();
            for (String key : settingDataKeys) {
                if (map.containsKey(key)) {
                    resultMap.put(key, map.get(key));
                }
            }
            if (!resultMap.isEmpty()) {
                resultList.add(resultMap);
            }
        }
        return resultList;
    }

    @Override
    public List<GoodsReceiveHeader> save(List<GoodsReceiveHeader> goodsReceiveHeaderList, Locale locale) throws CommonException {
        return null;
    }

    public GoodsReceiveHeader createGoodsReceiveHeader(GoodsReceiveHeader g){
        return goodsReceiveHeaderRepository.save(g);
    }

    @Transactional(rollbackFor = {CommonException.class , Exception.class})
    public GoodsReceiveDto createGoodsReceive(GoodsReceiveDto goodsReceive, Locale locale) throws CommonException {
        GoodsReceiveDto createListResult = new GoodsReceiveDto();

        try {

            LocalDateTime currentTime = LocalDateTime.now();
            if (!Objects.isNull(goodsReceive)) {

                List<APIErrorDetail> errorDetails;

                // Check Goods Receive Detail
                errorDetails = goodsReceiveDetailService.checkGoodsReceiveDetail(goodsReceive , locale);

                if (!CollectionUtils.isEmpty(errorDetails)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.CHECK_EXISTS)
                            .setStatusCode(HttpStatus.BAD_REQUEST)
                            .setErrorDetails(errorDetails);
                }

                GoodsReceiveHeader createGoodsHeader = createGoodsReceiveHeader(GoodsReceiveHeader.builder()
                        .goodsReceiveHeaderNo(goodsReceive.getGoodsReceiveHeaderNo())
                        .createdAt(currentTime)
                        .updatedAt(currentTime)
                        .deletedFlg(0)
                        .build());

                List<GoodsReceiveDetail> list = new ArrayList<>();
                for (GoodsReceiveDetail g:goodsReceive.getGoodsReceiveDetailList()) {
                    GoodsReceiveDetail goodsReceiveDetail = GoodsReceiveDetail.builder()
                            .goodsReceiveHeaderId(createGoodsHeader.getGoodsReceiveHeaderId())
                            .goodsReceiveDetailNo(g.getGoodsReceiveDetailNo())
                            .warehouseId(g.getWarehouseId())
                            .quantity(g.getQuantity())
                            .productId(g.getProductId())
                            .createdAt(currentTime)
                            .updatedAt(currentTime)
                            .deletedFlg(0)
                            .build();
                    list.add(goodsReceiveDetail);
                }
                List<GoodsReceiveDetail> creatGoodsDetail = goodsReceiveDetailRepository.saveAllAndFlush(list);

                createListResult = new GoodsReceiveDto(createGoodsHeader.getGoodsReceiveHeaderId() , createGoodsHeader.getGoodsReceiveHeaderNo(), creatGoodsDetail);
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

        return  createListResult ;
    }

    @Override
    public Optional<GoodsReceiveHeader> findById(Long id) {
        return goodsReceiveHeaderRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        goodsReceiveHeaderRepository.deleteById(id);
    }
}
