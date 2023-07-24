package com.rl.train.member.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class MemberLoginReq {
    
    @NotBlank(message = "[手机号]不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号码格式错误")
    private String mobile;
    
    
    @NotBlank(message = "[验证码不能为空]")
    @Length(min = 4, max = 4, message = "验证码长度出现错误")
    private String code;
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public String toString() {
        return "MemberLoginReq{" + "mobile='" + mobile + '\'' + ", code='" + code + '\'' + '}';
    }
}
