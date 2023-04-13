package com.ll.gramgram.base.security.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OAuth2AttributeFactory implements Function<Map<String, Object>, OAuth2Attribute> {
    GOOGLE(AuthProvider.GOOGLE) {
        public OAuth2Attribute apply(Map<String, Object> rawAttributes) {
            String username = String.format(usernameFormat, this.getValue().getValue(), rawAttributes.get("sub")); // wierd
            String email = (String) rawAttributes.get("email");
            String profileImage = (String) rawAttributes.get("picture");

            return OAuth2Attribute.builder()
                    .username(username)
                    .email(email)
                    .profileImage(profileImage)
                    .attributes(rawAttributes)
                    .build();
        }
    },
    KAKAO(AuthProvider.KAKAO) {
        public OAuth2Attribute apply(Map<String, Object> rawAttributes) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) rawAttributes.get("kakao_account");
            Map<String, Object> properties = (Map<String, Object>) rawAttributes.get("properties");

            String username = String.format(usernameFormat, this.getValue().getValue(), rawAttributes.get("id"));
            String email = (String) kakaoAccount.get("email");
            String profileImage = (String) properties.get("profile_image");

            return OAuth2Attribute.builder()
                    .username(username)
                    .email(email)
                    .profileImage(profileImage)
                    .attributes(rawAttributes)
                    .build();
        }
    },
    NAVER(AuthProvider.NAVER) {
        public OAuth2Attribute apply(Map<String, Object> rawAttributes) {
            Map<String, Object> attributes = (Map<String, Object>) rawAttributes.get("response");

            String username = String.format(usernameFormat, this.getValue().getValue(), attributes.get("id"));
            String email = (String) attributes.get("email");
            String profileImage = (String) attributes.get("profile_image");

            return OAuth2Attribute.builder()
                    .username(username)
                    .email(email)
                    .profileImage(profileImage)
                    .attributes(attributes)
                    .build();
        }
    };

    private static final String usernameFormat = "%s__%s";
    @Getter
    private final AuthProvider value;

    public static OAuth2AttributeFactory of(AuthProvider authProvider) {
        return Arrays.stream(values())
                .filter(v -> v.value.equals(authProvider))
                .findFirst()
                .orElse(GOOGLE);
    }
}
