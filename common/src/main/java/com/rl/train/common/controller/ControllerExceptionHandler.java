package com.rl.train.common.controller;

import com.rl.train.common.exception.BusinessException;
import com.rl.train.common.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    
    /**
     * 所有异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResp exceptionHandler(Exception e) throws Exception{
        CommonResp<Object> commonResp = new CommonResp<>();
        LOG.info("系统异常: ", e);
        commonResp.setSuccess(false);
        commonResp.setMessage("系统出现异常，请联系管理员");
        return commonResp;
    }
    
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResp exceptionHandler(BusinessException e){
        CommonResp<Object> commonResp = new CommonResp<>();
        LOG.info("业务异常: {}", e.getAnEnum().getDesc());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getAnEnum().getDesc());
        return commonResp;
    }
    
    /*
    *
    * 校验异常
    * */
    
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonResp exceptionHandler(BindException e){
        CommonResp<Object> commonResp = new CommonResp<>();
        LOG.info("校验异常: {}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }
}
