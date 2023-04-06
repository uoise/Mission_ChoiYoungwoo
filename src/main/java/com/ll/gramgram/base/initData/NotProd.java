package com.ll.gramgram.base.initData;

import com.ll.gramgram.boundedContext.instaMember.service.InstaMemberService;
import com.ll.gramgram.boundedContext.likeablePerson.entity.AttractiveType;
import com.ll.gramgram.boundedContext.likeablePerson.service.LikeablePersonService;
import com.ll.gramgram.boundedContext.member.entity.Member;
import com.ll.gramgram.boundedContext.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.IntStream;

@Configuration
@Profile({"dev", "test"})
public class NotProd {
    @Bean
    CommandLineRunner initData(
            MemberService memberService,
            InstaMemberService instaMemberService,
            LikeablePersonService likeablePersonService
    ) {
        return args -> {
            Member memberAdmin = memberService.join("admin", "1234").getData();

            IntStream.range(1, 20).forEach(i -> memberService.join("user%d".formatted(i), "1234"));

            Member memberUser1 = memberService.findByUsername("user1").orElseThrow(() -> new UsernameNotFoundException("user1"));
            Member memberUser2 = memberService.findByUsername("user2").orElseThrow(() -> new UsernameNotFoundException("user2"));
            Member memberUser3 = memberService.findByUsername("user3").orElseThrow(() -> new UsernameNotFoundException("user3"));
            Member memberUser4 = memberService.findByUsername("user4").orElseThrow(() -> new UsernameNotFoundException("user4"));

            Member memberUser5ByKakao = memberService.whenSocialLogin("KAKAO", "KAKAO__2733144890").getData();

            instaMemberService.connect(memberUser2, "insta_user2", "M");
            instaMemberService.connect(memberUser3, "insta_user3", "W");
            instaMemberService.connect(memberUser4, "insta_user4", "M");
            instaMemberService.connect(memberUser5ByKakao, "uoise", "M");

            likeablePersonService.like(memberUser3, "insta_user4", AttractiveType.APPEARANCE);
            likeablePersonService.like(memberUser3, "insta_user100", AttractiveType.ABILITY);
            IntStream.range(1, 10).forEach(i -> likeablePersonService.like(memberUser5ByKakao, "insta_user%d".formatted(i), AttractiveType.findByCode((i % 3) + 1)));
        };
    }
}
