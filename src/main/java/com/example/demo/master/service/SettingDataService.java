package com.example.demo.master.service;

import com.example.demo.master.entities.SettingData;
import com.example.demo.master.repository.SettingDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingDataService {

    private  final SettingDataRepository settingDataRepository;

    public List<SettingData> getSettingDataByScreenId(Integer screenId) {
        return settingDataRepository.getListSettingData(screenId);
    }

}
