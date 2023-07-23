package com.rl.train.member.controller;

import com.rl.train.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
@RestController
public class MemberController {
    @Resource
    private MemberService memberService;
    
    @GetMapping("count")
    public Integer getCount(){
        return memberService.count();
    }
    
}
