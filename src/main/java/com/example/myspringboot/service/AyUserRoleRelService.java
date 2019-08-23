package com.example.myspringboot.service;

import com.example.myspringboot.model.AyUserRoleRel;

import java.util.List;

/**
 * 用户角色关联Service
 * @author ：dejavu111
 * @date ：Created in 2019/8/23 16:20
 */
public interface AyUserRoleRelService {
    List<AyUserRoleRel> findByUserId(String userId);
}
