package com.example.xedd.exception;

public class UsernameNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public String UsernameNotFoundException(String username) {
        return "Cannot find user " + username;
    }
}
