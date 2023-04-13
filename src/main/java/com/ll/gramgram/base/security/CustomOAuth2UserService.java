package com.ll.gramgram.base.security;

import com.ll.gramgram.base.security.entity.AuthProvider;
import com.ll.gramgram.base.security.entity.CustomUserFactory;
import com.ll.gramgram.base.security.entity.OAuth2Attribute;
import com.ll.gramgram.base.security.entity.OAuth2AttributeFactory;
import com.ll.gramgram.boundedContext.member.entity.Member;
import com.ll.gramgram.boundedContext.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberService memberService;

    // Oauth2 mapping
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        AuthProvider authProvider = AuthProvider.of(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2Attribute oAuth2Attribute = OAuth2AttributeFactory.of(authProvider).apply(oAuth2User.getAttributes());

        Member member = memberService.whenSocialLogin(authProvider, oAuth2Attribute.getUsername()).getData();

        oAuth2Attribute.setUsername(member.getUsername());
        oAuth2Attribute.setPassword(member.getPassword());
        oAuth2Attribute.setAuthorities(member.getGrantedAuthorities());

        return CustomUserFactory.convert(authProvider, oAuth2Attribute);
    }
}