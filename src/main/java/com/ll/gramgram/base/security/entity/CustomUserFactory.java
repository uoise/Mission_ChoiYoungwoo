package com.ll.gramgram.base.security.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.function.Function;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CustomUserFactory {
    GOOGLE(AuthProvider.GOOGLE, GoogleUser::of),
    KAKAO(AuthProvider.KAKAO, KakaoUser::of),
    NAVER(AuthProvider.NAVER, NaverUser::of);

    private final AuthProvider authProvider;
    private final Function<OAuth2Attribute, CustomOAuth2User> convertor;

    public static CustomOAuth2User convert(AuthProvider authProvider, OAuth2Attribute oAuth2Attribute) {
        return Arrays.stream(values())
                .filter(v -> v.authProvider.equals(authProvider))
                .findFirst()
                .orElse(GOOGLE)
                .convertor
                .apply(oAuth2Attribute);
    }
}
