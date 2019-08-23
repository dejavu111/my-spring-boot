package com.example.myspringboot.exception;

/**
 * @author ：dejavu111
 * @date ：Created in 2019/8/15 11:46
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {}

    public BusinessException(String message) {
        super(message);
    }
}
