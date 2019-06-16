package com.example.myspringboot.dao;

import com.example.myspringboot.model.AyUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author ：dejavu111
 * @date ：Created in 2019/6/16 14:18
 * @description：
 * @modified By：
 * @version: $
 */
@Mapper
@Repository
public interface AyUserDao {
    AyUser findByNameAndPassword(@Param("name") String name,@Param("password") String password);
}
