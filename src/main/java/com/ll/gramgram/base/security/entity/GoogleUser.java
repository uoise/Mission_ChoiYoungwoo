package com.ll.gramgram.base.security.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

public class GoogleUser extends CustomOAuth2User {
    private GoogleUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String email, String profileImage, Map<String, Object> attributes) {
        super(username, password, authorities, email, profileImage, attributes);
    }

    public static GoogleUser of(OAuth2Attribute oAuth2Attribute) {
        return new GoogleUser(oAuth2Attribute.getUsername(),
                oAuth2Attribute.getPassword(),
                oAuth2Attribute.getAuthorities(),
                oAuth2Attribute.getEmail(),
                oAuth2Attribute.getProfileImage(),
                oAuth2Attribute.getAttributes()
        );
    }
}
