package com.example.demo;

import com.example.demo.constant.MessageCode;
import com.example.demo.exception.CommonException;
import com.example.demo.master.repository.SettingDataRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class Menu {

    private  final SettingDataRepository settingDataRepository;

    private final MessageSource messageSource;

    @RequestMapping(value = {"/goods-receive","/",""})
    public ModelAndView menu(ModelAndView mav) {
        mav.setViewName("search/goodsReceive");
        try {

        } catch (Exception e) {
            return mav;
        }

        return mav;
    }

    @RequestMapping(value = {"/setting"})
    public ModelAndView setting(ModelAndView mav, HttpServletRequest request, Locale locale) throws CommonException{
        mav.setViewName("master/settingColumn");
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

        return mav;
    }

}
