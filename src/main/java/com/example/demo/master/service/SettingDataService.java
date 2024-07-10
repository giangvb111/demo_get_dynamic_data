package com.example.demo.master.service;

import com.example.demo.constant.MessageCode;
import com.example.demo.exception.CommonException;
import com.example.demo.master.entities.SettingData;
import com.example.demo.master.repository.SettingDataRepository;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SettingDataService implements GenericService<SettingData>{

    private  final SettingDataRepository settingDataRepository;

    private final MessageSource messageSource;

    public List<SettingData> getSettingDataByScreenId(Integer screenId) {
        return settingDataRepository.getListSettingData(screenId);
    }

    public List<String> getListColumnName(String tableName) {
//        List<String> columns  = new ArrayList<>();
//        Field[] fields = SettingData.class.getDeclaredFields();
//        for (Field f:fields) {
//            Column col = f.getAnnotation(Column.class);
//            if (col != null) {
//
//            }
//        }
        return settingDataRepository.listColumnName(tableName);
    }

    @Override
    public Page<SettingData> findAll(Pageable pageable) throws CommonException {
        return null;
    }

    @Override
    @Transactional(rollbackFor = {CommonException.class , Exception.class})
    public List<SettingData> save(List<SettingData> settingDataList , Locale locale) throws CommonException {
        List<SettingData> createSettingDataList = new ArrayList<>();
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            if (!CollectionUtils.isEmpty(settingDataList)) {
                Integer screenId  = settingDataList.stream().mapToInt(s  -> Math.toIntExact(s.getScreenId())).findFirst().orElse(-1);
                List<SettingData> list = settingDataList.stream()
                        .map(s -> SettingData.builder()
                                .screenId(s.getScreenId())
                                .columnName(s.getColumnName())
                                .tableName(s.getTableName())
                                .dataType(s.getDataType())
                                .columnWidth(s.getColumnWidth())
                                .status(1)
                                .createdAt(currentTime)
                                .updatedAt(currentTime)
                                .deletedFlg(0)
                                .build())
                        .toList();

                settingDataRepository.saveAllAndFlush(list);
                createSettingDataList = getSettingDataByScreenId(screenId);
            }
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR ,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return createSettingDataList;
    }

    @Transactional(rollbackFor = {Exception.class , CommonException.class})
    public List<SettingData> updateSettingData (List<SettingData> settingDataList , Locale locale) throws CommonException{
            List<SettingData> updateSettingList = new ArrayList<>();
        try {
            LocalDateTime currentTime  = LocalDateTime.now();
            if (!CollectionUtils.isEmpty(settingDataList)) {
                List<Long> settingDataIdList = settingDataList.stream()
                        .map(SettingData :: getId).toList();
                Integer screenId  = settingDataList.stream().mapToInt(s  -> Math.toIntExact(s.getScreenId())).findFirst().orElse(-1);
                List<SettingData> dataList = settingDataRepository.findSettingDataByIds(settingDataIdList);

                if (CollectionUtils.isEmpty(dataList)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.DATA_NOT_FOUND)
                            .setStatusCode(HttpStatus.NOT_FOUND)
                            .setMessage(messageSource.getMessage(
                                    MessageCode.DATA_NOT_FOUND,null , locale
                            ));
                }

                List<SettingData> list = settingDataList.stream()
                        .map(s -> SettingData.builder()
                                .id(s.getId())
                                .screenId(s.getScreenId())
                                .columnName(s.getColumnName())
                                .dataType(s.getDataType())
                                .tableName(s.getTableName())
                                .columnWidth(s.getColumnWidth())
                                .status(s.getStatus())
                                .createdAt(currentTime)
                                .updatedAt(currentTime)
                                .deletedFlg(0)
                                .build())
                        .toList();

                 settingDataRepository.saveAll(list);

                 updateSettingList = getSettingDataByScreenId(screenId);
            }
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return updateSettingList;
    }

    @Override
    public Optional<SettingData> findById(Long id) {
        return settingDataRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        settingDataRepository.deleteById(id);
    }

}
