package com.rl.train.common.exception;

public class BusinessException extends RuntimeException{
    private BusinessExceptionEnum anEnum;
    
    public BusinessException(BusinessExceptionEnum anEnum) {
        this.anEnum = anEnum;
    }
    
    public BusinessExceptionEnum getAnEnum() {
        return anEnum;
    }
    
    public void BusinessExceptionEnum(BusinessExceptionEnum anEnum) {
        this.anEnum = anEnum;
    }
}
