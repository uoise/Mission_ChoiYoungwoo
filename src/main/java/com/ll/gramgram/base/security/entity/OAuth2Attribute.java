package com.ll.gramgram.base.security.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

// OAuth2 Attribute DTO
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2Attribute {
    private String username;
    private String password;
    private String email;
    private String profileImage;
    private Map<String, Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;
}
