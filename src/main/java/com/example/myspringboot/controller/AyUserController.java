package com.example.myspringboot.controller;

import com.example.myspringboot.model.AyUser;
import com.example.myspringboot.service.AyUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("ayUser")
public class AyUserController {
    @Resource
    private AyUserService ayUserService;
    @RequestMapping("/test")
    public String test(Model model){
        List<AyUser> ayUsers = ayUserService.findAll();
        model.addAttribute("users",ayUsers);
        return "ayUser";
    }
}
