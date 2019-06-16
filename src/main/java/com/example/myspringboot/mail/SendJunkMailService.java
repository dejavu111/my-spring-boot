package com.example.myspringboot.mail;

import com.example.myspringboot.model.AyUser;

import java.util.List;

public interface SendJunkMailService {
    boolean sendJunkMail(List<AyUser> ayUserList);
}
