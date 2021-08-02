package com.furenqiang.business.exception;

import com.furenqiang.business.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * 集中处理所有异常
 * */
@Slf4j
@RestControllerAdvice(basePackages = "com.furenqiang.gulimall.product.controller")
public class BusinessExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private R handleVaildExcepction(MethodArgumentNotValidException e){
        log.error("校验出现异常{},异常类型{}",e.getMessage(),e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        HashMap<String,String> errMap=new HashMap<String,String>();
        if (bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach((item)->{
                errMap.put(item.getField(),item.getDefaultMessage());
            });
        }
        return R.error(400,"参数校验不合法").put("data",errMap);
    }

    @ExceptionHandler(value = Throwable.class)
    private R handleVaildExcepction(Throwable throwable){
        log.error("校验出现异常{},异常类型{}",throwable.getMessage(),throwable.getClass());
        return R.error();
    }
}
