package com.rl.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.rl.train.common.exception.BusinessException;
import com.rl.train.common.exception.BusinessExceptionEnum;
import com.rl.train.common.util.JwtUtil;
import com.rl.train.common.util.SnowUtil;
import com.rl.train.member.domain.Member;
import com.rl.train.member.domain.MemberExample;
import com.rl.train.member.mapper.MemberMapper;
import com.rl.train.member.req.MemberLoginReq;
import com.rl.train.member.req.MemberRegisterReq;
import com.rl.train.member.req.MemberSendCodeReq;
import com.rl.train.member.resp.MemberLoginResp;
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
        Member memberDb = selectByMobile(req.getMobile());
        if (ObjectUtil.isNotEmpty(memberDb)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }
        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(req.getMobile());
        mapper.insert(member);
        return member.getId();
    }
    
    public void sendCode(MemberSendCodeReq req){
    
        Member memberDb = selectByMobile(req.getMobile());
        // 如果手机号不存在，则插入记录
        if (ObjectUtil.isNull(memberDb)){
            Member member = new Member();
            member.setMobile(req.getMobile());
            member.setId(SnowUtil.getSnowflakeNextId());
            mapper.insert(member);
        }
        // 生成验证码
        String code = RandomUtil.randomString(4);
        LOG.info("手机验证码为: {}", code);
        // TODO 保存报信短信记录表:
        // 保存记录表: 手机号， 短信验证码， 有效期， 是否已经使用， 业务类型，发送时间，使用时间
        
        // TODO 对接短信通道，发送短信
    }
    
    public MemberLoginResp login(MemberLoginReq req){
        String mobile = req.getMobile();
        String code = req.getCode();
        Member memberDb = selectByMobile(mobile);
        // 如果手机号不存在，则插入记录
        if (ObjectUtil.isNull(memberDb)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }
        
        // 校验短信验证码
        if (!"8888".equals(code)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }
    
        MemberLoginResp memberLoginResp = BeanUtil.copyProperties(memberDb, MemberLoginResp.class);
        String token = JwtUtil.createToken(memberLoginResp.getId(), memberLoginResp.getMobile());
        memberLoginResp.setToken(token);
        return memberLoginResp;
    }
    
    private Member selectByMobile(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = mapper.selectByExample(memberExample);
        if (CollUtil.isEmpty(members)){
            return null;
        }
        return members.get(0);
    }
}
