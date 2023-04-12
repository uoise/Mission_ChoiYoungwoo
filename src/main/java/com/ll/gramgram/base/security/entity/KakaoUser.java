package com.ll.gramgram.base.security.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

public class KakaoUser extends CustomOAuth2User {
    private KakaoUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String email, String profileImage, Map<String, Object> attributes) {
        super(username, password, authorities, email, profileImage, attributes);
    }

    public static KakaoUser of(OAuth2Attribute oAuth2Attribute) {
        return new KakaoUser(oAuth2Attribute.getUsername(),
                oAuth2Attribute.getPassword(),
                oAuth2Attribute.getAuthorities(),
                oAuth2Attribute.getEmail(),
                oAuth2Attribute.getProfileImage(),
                oAuth2Attribute.getAttributes()
        );
    }
}
