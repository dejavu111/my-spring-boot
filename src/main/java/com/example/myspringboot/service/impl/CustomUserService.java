package com.example.myspringboot.service.impl;

import com.example.myspringboot.exception.BusinessException;
import com.example.myspringboot.model.AyUser;
import com.example.myspringboot.model.AyUserRoleRel;
import com.example.myspringboot.service.AyRoleService;
import com.example.myspringboot.service.AyUserRoleRelService;
import com.example.myspringboot.service.AyUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：dejavu111
 * @date ：Created in 2019/8/23 16:34
 */
public class CustomUserService implements UserDetailsService {
    @Resource
    private AyUserService ayUserService;
    @Resource
    private AyUserRoleRelService ayUserRoleRelService;
    @Resource
    private AyRoleService ayRoleService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        AyUser ayUser = ayUserService.findByName(name).get(0);
        if(ayUser == null){
            throw new BusinessException("用户不存在");
        }
//        获取用户所有的关联角色
        List<AyUserRoleRel> ayUserRoleRels = ayUserRoleRelService.findByUserId(ayUser.getId());
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        if(ayUserRoleRels != null && ayUserRoleRels.size() > 0) {
            for(AyUserRoleRel rel:ayUserRoleRels) {
//                获取用户关联角色名称
                String roleName = ayRoleService.find(rel.getRoleId()).getName();
                authorityList.add(new SimpleGrantedAuthority(roleName));
            }
        }
        return new User(ayUser.getName(),ayUser.getPassword(),authorityList);
    }
}
