package com.hmsgtech.exception;

/**
 * Created by 晓丰 on 2016/6/15.
 */
public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(String message) {
        super(message);
    }
}
