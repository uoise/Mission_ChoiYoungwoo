package com.ll.gramgram.base.security.entity;

import java.util.Arrays;
import java.util.function.Function;

public enum CustomUserFactory {
    GOOGLE(OAuth2UserProvider.GOOGLE, GoogleUser::of),
    KAKAO(OAuth2UserProvider.KAKAO, KakaoUser::of),
    NAVER(OAuth2UserProvider.NAVER, NaverUser::of);

    private final OAuth2UserProvider oAuth2UserProvider;
    private final Function<OAuth2Attribute, CustomOAuth2User> convertor;

    CustomUserFactory(OAuth2UserProvider oAuth2UserProvider, Function<OAuth2Attribute, CustomOAuth2User> convertor) {
        this.oAuth2UserProvider = oAuth2UserProvider;
        this.convertor = convertor;
    }

    public static CustomOAuth2User convert(OAuth2UserProvider oAuth2UserProvider, OAuth2Attribute oAuth2Attribute) {
        return Arrays.stream(values())
                .filter(v -> v.oAuth2UserProvider.equals(oAuth2UserProvider))
                .findFirst()
                .orElse(GOOGLE)
                .convertor
                .apply(oAuth2Attribute);
    }
}
