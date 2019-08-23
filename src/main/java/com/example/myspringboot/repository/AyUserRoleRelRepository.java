package com.example.myspringboot.repository;

import com.example.myspringboot.model.AyRole;
import com.example.myspringboot.model.AyUserRoleRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author ：dejavu111
 * @date ：Created in 2019/8/23 16:16
 */
public interface AyUserRoleRelRepository extends JpaRepository<AyUserRoleRel,String> {
    List<AyUserRoleRel> findByUserId(@Param("userId") String userID);
}
