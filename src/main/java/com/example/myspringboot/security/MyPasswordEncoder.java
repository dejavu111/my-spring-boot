package com.example.myspringboot.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ：dejavu111
 * @date ：Created in 2019/8/23 17:21
 */
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
