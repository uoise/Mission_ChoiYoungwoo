package com.ll.gramgram.base.security.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

abstract class CustomOAuth2User extends User implements OAuth2User {
    @Getter
    private final String email;
    @Getter
    private final String profileImage;
    @Getter
    private final Map<String, Object> attributes;

    protected CustomOAuth2User(String username, String password, Collection<? extends GrantedAuthority> authorities, String email, String profileImage, Map<String, Object> attributes) {
        super(username, password, authorities);
        this.email = email;
        this.profileImage = profileImage;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return super.getUsername();
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }
}