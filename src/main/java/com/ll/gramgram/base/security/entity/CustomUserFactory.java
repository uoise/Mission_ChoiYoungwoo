package com.ll.gramgram.base.security.entity;

import java.util.Arrays;
import java.util.function.Function;

public enum CustomUserFactory implements Function<OAuth2Attribute, CustomOAuth2User> {
    GOOGLE(OAuth2UserProvider.GOOGLE) {
        @Override
        public CustomOAuth2User apply(OAuth2Attribute oAuth2Attribute) {
            return GoogleUser.of(oAuth2Attribute);
        }
    },
    KAKAO(OAuth2UserProvider.KAKAO) {
        @Override
        public CustomOAuth2User apply(OAuth2Attribute oAuth2Attribute) {
            return KakaoUser.of(oAuth2Attribute);
        }
    },
    NAVER(OAuth2UserProvider.NAVER) {
        @Override
        public CustomOAuth2User apply(OAuth2Attribute oAuth2Attribute) {
            return NaverUser.of(oAuth2Attribute);
        }
    };
    private final OAuth2UserProvider oAuth2UserProvider;

    CustomUserFactory(OAuth2UserProvider oAuth2UserProvider) {
        this.oAuth2UserProvider = oAuth2UserProvider;
    }

    public static CustomUserFactory of(OAuth2UserProvider oAuth2UserProvider) {
        return Arrays.stream(values())
                .filter(v -> v.oAuth2UserProvider.equals(oAuth2UserProvider))
                .findFirst()
                .orElse(GOOGLE);
    }
}
