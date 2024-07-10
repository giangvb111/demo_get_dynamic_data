package com.example.demo.master.service;

import com.example.demo.constant.MessageCode;
import com.example.demo.exception.CommonException;
import com.example.demo.master.dto.SettingDataDtoImpl;
import com.example.demo.master.dto.SettingDataDtos;
import com.example.demo.master.entities.SettingData;
import com.example.demo.master.repository.SettingDataRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SettingDataService implements GenericService<SettingData>{

    private  final SettingDataRepository settingDataRepository;

    private final MessageSource messageSource;

    private final ModelMapper modelMapper;

    public ModelAndView initSetting(ModelAndView mav , HttpServletRequest request , Locale locale) throws CommonException{
        mav.setViewName("screenName");
        try {
            Integer screenId = settingDataRepository.getScreenIdByUrl(request.getRequestURI());
            if (Objects.isNull(screenId)) {
                throw new CommonException()
                        .setErrorCode(MessageCode.DATA_NOT_FOUND)
                        .setMessage(messageSource.getMessage(MessageCode.DATA_NOT_FOUND, null, locale))
                        .setStatusCode(HttpStatus.BAD_REQUEST);
            }

            mav.addObject("screenOption", settingDataRepository.screenOptionList(screenId));
        }catch (CommonException e){
            throw e;
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR ,
                    e.getMessage() ,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return mav ;
    }

    public List<SettingDataDtoImpl> getSettingDataByScreenId(Integer screenId) {
        List<SettingDataDtoImpl> resultList = new ArrayList<>();
        if (!Objects.isNull(screenId)) {
            List<SettingDataDtos> settingDataDtoList = settingDataRepository.getListSettingData(screenId);
            List<String> tableNameList = settingDataRepository.listTableName(screenId);
            resultList = settingDataDtoList.stream()
                    .map( s -> SettingDataDtoImpl.builder()
                            .id(s.getId())
                            .screenId(s.getScreenId())
                            .columnName(s.getColumnName())
                            .tableName(s.getTableName())
                            .dataType(s.getDataType())
                            .columnWidth(s.getColumnWidth())
                            .status(s.getStatus())
                            .tableNameList(tableNameList)
                            .build()
                    )
                    .toList();
        }
        return resultList;
    }

    public List<String> getListColumnName(String tableName) {
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
                                .id(s.getId())
                                .screenId(s.getScreenId())
                                .columnName(s.getColumnName())
                                .tableName(s.getTableName())
                                .dataType(s.getDataType())
                                .columnWidth(s.getColumnWidth())
                                .status(s.getStatus())
                                .createdAt(currentTime)
                                .updatedAt(currentTime)
                                .deletedFlg(0)
                                .build())
                        .toList();

                settingDataRepository.saveAllAndFlush(list);
                createSettingDataList = modelMapper.map(getSettingDataByScreenId(screenId), new TypeToken<List<SettingData>>(){}.getType());
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

                 updateSettingList = modelMapper.map(getSettingDataByScreenId(screenId), new TypeToken<List<SettingData>>(){}.getType());
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
