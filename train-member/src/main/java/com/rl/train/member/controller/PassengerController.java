package com.rl.train.member.controller;

import com.rl.train.common.context.LoginMemberContext;
import com.rl.train.common.resp.CommonResp;
import com.rl.train.common.resp.PageResp;
import com.rl.train.member.req.PassengerQueryReq;
import com.rl.train.member.req.PassengerSaveReq;
import com.rl.train.member.resp.PassengerQueryResp;
import com.rl.train.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("passenger")
public class PassengerController {
    
    @Resource
    private PassengerService passengerService;
    
    @PostMapping("save")
    public CommonResp<Object> save(@Valid @RequestBody PassengerSaveReq req){
        passengerService.save(req);
        return new CommonResp<>();
    }
    
    @GetMapping("/query-list")
    public CommonResp<PageResp<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req){
        req.setMemberId(LoginMemberContext.getId());
        PageResp<PassengerQueryResp> passengerQueryRespPageResp = passengerService.queryList(req);
        System.out.println(passengerQueryRespPageResp);
        return new CommonResp<>(passengerQueryRespPageResp);
    }
    
}
