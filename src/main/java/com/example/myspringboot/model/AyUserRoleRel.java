package com.example.myspringboot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ：dejavu111
 * @date ：Created in 2019/8/23 16:11
 */
@Entity
@Table(name = "ay_user_role_rel")
public class AyUserRoleRel {
    @Id
    private String userId;
    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
