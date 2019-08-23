package com.example.myspringboot.service.impl;

import com.example.myspringboot.model.AyRole;
import com.example.myspringboot.repository.AyRoleRepository;
import com.example.myspringboot.service.AyRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：dejavu111
 * @date ：Created in 2019/8/23 16:27
 */
@Service
public class AyRoleServiceImpl implements AyRoleService {
    @Resource
    private AyRoleRepository ayRoleRepository;


    @Override
    public AyRole find(String id) {
        return ayRoleRepository.findById(id).get();
    }
}
