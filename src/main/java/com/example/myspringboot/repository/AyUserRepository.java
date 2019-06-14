package com.example.myspringboot.repository;

import com.example.myspringboot.model.AyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;
import java.util.List;

/**
 * 用户Repository
 * @author dejavu111
 * @date 2019/1/13
 */

public interface AyUserRepository extends JpaRepository<AyUser,String> {
    /**
     * 描述：通过名字相等查询，参数name
     * 相当于：select u from ay_user u where u.name = ?
     */
    List<AyUser> findByName(String name);

    /**
     * 描述：通过名字like查询，参数为name
     * 相当于：select u from ay_user u where u.name like ?
     */
    List<AyUser> findByNameLike(String name);

    /**
     * 描述：相当于通过主键id集合查询，参数为id集合
     * 相当于：select u from ay_user u where id in(?,?,?)
     * @param ids
     */
    List<AyUser> findByIdIn(Collection<String> ids);


}
