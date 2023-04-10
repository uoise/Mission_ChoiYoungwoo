package com.ll.gramgram.boundedContext.likeablePerson.service;

import com.ll.gramgram.base.rsData.RsData;
import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import com.ll.gramgram.boundedContext.instaMember.service.InstaMemberService;
import com.ll.gramgram.boundedContext.likeablePerson.entity.AttractiveType;
import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import com.ll.gramgram.boundedContext.member.entity.Member;
import com.ll.gramgram.boundedContext.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class LikeablePersonServiceTests {
    private final String TEST_MEMBER_USERNAME = "user2";
    private final String TEST_OAUTH_USERNAME = "KAKAO__2733144890";
    private final String TEST_INSTA_USERNAME = "Insta_user3";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private LikeablePersonService likeablePersonService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private InstaMemberService instaMemberService;

    @Test
    @DisplayName("likeablePerson add no insta autogen")
    void t001() throws Exception {
        Member m1 = memberService.findByUsername(TEST_MEMBER_USERNAME).get();
        assertThat(m1).isNotNull();

        InstaMember i1 = instaMemberService.findByUsername(TEST_INSTA_USERNAME).get();
        assertThat(i1).isNotNull();

        AttractiveType attractiveType = AttractiveType.findByCode(1);
        RsData<LikeablePerson> likeablePersonRsData = likeablePersonService.like(m1, i1.getUsername(), attractiveType);
        assertThat(likeablePersonRsData.isSuccess()).isTrue();
    }

    @Test
    @DisplayName("likeablePerson add with insta autogen")
    void t002() throws Exception {
        Optional<Member> m1 = memberService.findByUsername(TEST_MEMBER_USERNAME);
        assertThat(m1).isPresent();

        Optional<InstaMember> i1 = instaMemberService.findByUsername(TEST_INSTA_USERNAME + "!!");
        assertThat(i1).isEmpty();

        AttractiveType attractiveType = AttractiveType.findByCode(1);
        RsData<LikeablePerson> likeablePersonRsData = likeablePersonService.like(m1.get(), TEST_INSTA_USERNAME + "!!", attractiveType);
        assertThat(likeablePersonRsData.isSuccess()).isTrue();
        i1 = instaMemberService.findByUsername(TEST_INSTA_USERNAME + "!!");
        assertThat(i1.get().getGender()).isEqualTo("U");
    }
}
