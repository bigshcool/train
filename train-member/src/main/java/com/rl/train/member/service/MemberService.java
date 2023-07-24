package com.rl.train.member.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.rl.train.common.exception.BusinessException;
import com.rl.train.common.exception.BusinessExceptionEnum;
import com.rl.train.common.util.SnowUtil;
import com.rl.train.member.domain.Member;
import com.rl.train.member.domain.MemberExample;
import com.rl.train.member.mapper.MemberMapper;
import com.rl.train.member.req.MemberRegisterReq;
import com.rl.train.member.req.MemberSendCodeReq;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    
    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);
    
    @Resource
    private MemberMapper mapper;
    
    public int count(){
       return Math.toIntExact(mapper.countByExample(null));
    }
    
    public Long register(MemberRegisterReq req){
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(req.getMobile());
        List<Member> members = mapper.selectByExample(memberExample);
        if (CollUtil.isNotEmpty(members)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }
        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(req.getMobile());
        mapper.insert(member);
        return member.getId();
    }
    
    public void sendCode(MemberSendCodeReq req){
        
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(req.getMobile());
        List<Member> members = mapper.selectByExample(memberExample);
        // 如果手机号不存在，则插入记录
        if (CollUtil.isEmpty(members)){
            Member member = new Member();
            member.setMobile(req.getMobile());
            member.setId(SnowUtil.getSnowflakeNextId());
            mapper.insert(member);
        }
        // 生成验证码
        String code = RandomUtil.randomString(4);
        LOG.info("手机验证码为: {}", code);
        // 保存报信短信记录表:
        // 保存记录表: 手机号， 短信验证码， 有效期， 是否已经使用， 业务类型，发送时间，使用时间
        
        // 对接短信通道，发送短信
    }
}
