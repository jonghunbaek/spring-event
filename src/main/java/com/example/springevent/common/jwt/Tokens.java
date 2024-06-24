package com.example.springevent.common.jwt;

import lombok.Getter;

@Getter
public class Tokens {

    private String accessToken;
    private String refreshToken;

    public Tokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
