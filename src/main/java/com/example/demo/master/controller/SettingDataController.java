package com.example.demo.master.controller;

import com.example.demo.exception.CommonException;
import com.example.demo.master.entities.SettingData;
import com.example.demo.master.service.SettingDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/setting")
public class SettingDataController {

    private final SettingDataService settingDataService;

    @GetMapping(value = "/get-column-by-screen-id")
    public ResponseEntity<List<SettingData>> addColumn(@RequestParam("screenId") Integer screenId) throws CommonException {
        return ResponseEntity.ok(settingDataService.getSettingDataByScreenId(screenId));
    }

    @PostMapping(value = "/add-column")
    public ResponseEntity<List<SettingData>> addColumn(@RequestBody List<SettingData> settingDataList, Locale locale) throws CommonException{
        return  ResponseEntity.ok(settingDataService.save(settingDataList ,locale));
    }

    @PostMapping(value = "/update-column")
    public ResponseEntity<List<SettingData>> updateColumn(@RequestBody List<SettingData> settingDataList , Locale locale) throws CommonException{
       return  ResponseEntity.ok(settingDataService.updateSettingData(settingDataList , locale)) ;
    }

    @GetMapping(value = "/list-column-name")
    public ResponseEntity<List<String>> getListColumnName(@RequestParam("tableName") String tableName) {
        return ResponseEntity.ok(settingDataService.getListColumnName(tableName));
    }

}
