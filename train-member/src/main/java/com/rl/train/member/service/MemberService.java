package com.rl.train.member.service;

import com.rl.train.member.mapper.MemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    
    @Resource
    private MemberMapper mapper;
    
    public int count(){
       return mapper.count();
    }
}
