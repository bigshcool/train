package com.rl.train.${module}.controller;

import com.rl.train.common.context.LoginMemberContext;
import com.rl.train.common.resp.CommonResp;
import com.rl.train.common.resp.PageResp;
import com.rl.train.${module}.req.${Domain}QueryReq;
import com.rl.train.${module}.req.${Domain}SaveReq;
import com.rl.train.${module}.resp.${Domain}QueryResp;
import com.rl.train.${module}.service.${Domain}Service;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${do_main}")
public class ${Domain}Controller {

    @Resource
    private ${Domain}Service ${domain}Service;

    @PostMapping("save")
    public CommonResp<Object> save(@Valid @RequestBody ${Domain}SaveReq req){
        ${domain}Service.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<${Domain}QueryResp>> queryList(@Valid ${Domain}QueryReq req){
        req.setMemberId(LoginMemberContext.getId());
        PageResp<${Domain}QueryResp> ${domain}QueryRespPageResp = ${domain}Service.queryList(req);
        System.out.println(${domain}QueryRespPageResp);
        return new CommonResp<>(${domain}QueryRespPageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        ${domain}Service.delete(id);
        return new CommonResp<>();
    }
}
