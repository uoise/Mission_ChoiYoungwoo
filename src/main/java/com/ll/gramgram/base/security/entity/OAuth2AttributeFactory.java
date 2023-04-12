package com.ll.gramgram.base.security.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuth2AttributeFactory implements Function<Map<String, Object>, OAuth2Attribute> {
    GOOGLE(OAuth2UserProvider.GOOGLE) {
        public OAuth2Attribute apply(Map<String, Object> rawAttributes) {
            String username = String.format(usernameFormat, this.getValue().getValue(), rawAttributes.get("sub")); // wierd
            String email = (String) rawAttributes.get("email");
            String profileImage = (String) rawAttributes.get("picture");

            return new OAuth2Attribute(username, email, profileImage, rawAttributes);
        }
    },
    KAKAO(OAuth2UserProvider.KAKAO) {
        public OAuth2Attribute apply(Map<String, Object> rawAttributes) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) rawAttributes.get("kakao_account");
            Map<String, Object> properties = (Map<String, Object>) rawAttributes.get("properties");

            String username = String.format(usernameFormat, this.getValue().getValue(), rawAttributes.get("id"));
            String email = (String) kakaoAccount.get("email");
            String profileImage = (String) properties.get("profile_image");

            return new OAuth2Attribute(username, email, profileImage, rawAttributes);
        }
    },
    NAVER(OAuth2UserProvider.NAVER) {
        public OAuth2Attribute apply(Map<String, Object> rawAttributes) {
            Map<String, Object> attributes = (Map<String, Object>) rawAttributes.get("response");

            String username = String.format(usernameFormat, this.getValue().getValue(), attributes.get("id"));
            String email = (String) attributes.get("email");
            String profileImage = (String) attributes.get("profile_image");

            return new OAuth2Attribute(username, email, profileImage, attributes);
        }
    };

    private static final String usernameFormat = "%s__%s";
    @Getter
    private final OAuth2UserProvider value;

    OAuth2AttributeFactory(OAuth2UserProvider value) {
        this.value = value;
    }

    public static OAuth2AttributeFactory of(OAuth2UserProvider oAuth2UserProvider) {
        return Arrays.stream(values())
                .filter(v -> v.value.equals(oAuth2UserProvider))
                .findFirst()
                .orElse(GOOGLE);
    }
}
