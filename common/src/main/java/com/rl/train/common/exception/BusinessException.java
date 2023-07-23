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
    
    @Override
    public Throwable fillInStackTrace() {
        // 自定义异常打印不进入堆栈，进一步加强性能
        return this;
    }
}
