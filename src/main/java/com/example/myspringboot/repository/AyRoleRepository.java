package com.example.myspringboot.repository;

import com.example.myspringboot.model.AyRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户角色Respository
 * @author ：dejavu111
 * @date ：Created in 2019/8/23 16:13
 */

public interface AyRoleRepository extends JpaRepository<AyRole,String> {
}
