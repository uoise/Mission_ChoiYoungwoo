package com.ll.gramgram.base.security.entity;

import lombok.Getter;

import java.util.Arrays;

public enum OAuth2UserProvider {
    GOOGLE("GOOGLE"),
    KAKAO("KAKAO"),
    NAVER("NAVER");

    @Getter
    private final String value;

    OAuth2UserProvider(String value) {
        this.value = value;
    }

    public static OAuth2UserProvider of(String value) {
        return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElse(GOOGLE);
    }
}
