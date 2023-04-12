package com.ll.gramgram.base.security.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class OAuth2Attribute {
    private String username;
    private String password;
    private String email;
    private String profileImage;
    private Map<String, Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;

    public OAuth2Attribute(String username, String email, String profileImage, Map<String, Object> attributes) {
        this.username = username;
        this.email = email;
        this.profileImage = profileImage;
        this.attributes = attributes;
    }
}
