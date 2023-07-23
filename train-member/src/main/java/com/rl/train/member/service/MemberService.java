package com.rl.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.rl.train.member.domain.Member;
import com.rl.train.member.domain.MemberExample;
import com.rl.train.member.mapper.MemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    
    @Resource
    private MemberMapper mapper;
    
    public int count(){
       return Math.toIntExact(mapper.countByExample(null));
    }
    
    public Long register(String mobile){
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = mapper.selectByExample(memberExample);
        if (CollUtil.isNotEmpty(members)){
            return members.get(0).getId();
        }
        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);
        mapper.insert(member);
        return member.getId();
    }
}
