package com.example.myspringboot.security;

import com.example.myspringboot.service.impl.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.naming.AuthenticationException;

/**
 * @author ：dejavu111
 * @date ：Created in 2019/8/23 10:46
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // 路由策略和访问权限的简单配置
        http
                // 启用默认登录页面
                .formLogin()
                // 登录失败返回URL:/login?error
                .failureUrl("/login?error")
                // 登录成功跳转URL，这里跳转到用户首页
                .defaultSuccessUrl("/ayUser/test")
                // 登录页面全部权限可访问
                .permitAll();
        super.configure(http);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .inMemoryAuthentication()
                .passwordEncoder(new MyPasswordEncoder())
                .withUser("alan").password("123456").roles("ADMIN")
                .and()
                .withUser("ayi").password("123456").roles("USER");
    }

    @Bean
    public CustomUserService customUserService() {
        return new CustomUserService();
    }


}
