package com.ll.gramgram.base.security.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AuthProvider {
    GRAMGRAM("GRAMGRAM"),
    GOOGLE("GOOGLE"),
    KAKAO("KAKAO"),
    NAVER("NAVER");

    private final String value;

    public static AuthProvider of(String value) {
        return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElse(GRAMGRAM); //
    }
}
