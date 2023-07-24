package com.rl.train.member.controller;

import com.rl.train.common.resp.CommonResp;
import com.rl.train.member.req.MemberLoginReq;
import com.rl.train.member.req.MemberRegisterReq;
import com.rl.train.member.req.MemberSendCodeReq;
import com.rl.train.member.resp.MemberLoginResp;
import com.rl.train.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
@RestController
public class MemberController {
    @Resource
    private MemberService memberService;
    
    @GetMapping("/count")
    public CommonResp<Integer> getCount(){
        int count = memberService.count();
        CommonResp<Integer> resp = new CommonResp<>();
        resp.setMessage("获取成员数量成功");
        resp.setContent(count);
        return resp;
    }
    
    @PostMapping("/register")
    public CommonResp<Long> register(@Valid MemberRegisterReq req){
        CommonResp<Long> resp = new CommonResp<>();
        resp.setMessage("注册成功");
        resp.setContent(memberService.register(req));
        return resp;
    }
    
    @PostMapping("/send-code")
    public CommonResp<Long> sendCode(@Valid MemberSendCodeReq req){
        CommonResp<Long> resp = new CommonResp<>();
        memberService.sendCode(req);
        return resp;
    }
    
    @PostMapping("/login")
    public CommonResp<MemberLoginResp> login(@Valid MemberLoginReq req){
        MemberLoginResp memberVO = memberService.login(req);
        return new CommonResp<MemberLoginResp>(memberVO);
    }
    
}
