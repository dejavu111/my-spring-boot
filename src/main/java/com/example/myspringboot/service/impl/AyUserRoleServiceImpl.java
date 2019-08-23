package com.example.myspringboot.service.impl;

import com.example.myspringboot.model.AyUserRoleRel;
import com.example.myspringboot.repository.AyUserRepository;
import com.example.myspringboot.repository.AyUserRoleRelRepository;
import com.example.myspringboot.service.AyUserRoleRelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户角色关联Service
 * @author ：dejavu111
 * @date ：Created in 2019/8/23 16:29
 */
@Service
public class AyUserRoleServiceImpl implements AyUserRoleRelService {
    @Resource
    private AyUserRoleRelRepository ayUserRoleRelRepository;
    @Override
    public List<AyUserRoleRel> findByUserId(String userId) {
        return ayUserRoleRelRepository.findByUserId(userId);
    }


}
