package com.ll.gramgram.boundedContext.likeablePerson.controller;

import com.ll.gramgram.base.rsData.RsData;
import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import com.ll.gramgram.boundedContext.instaMember.service.InstaMemberService;
import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import com.ll.gramgram.boundedContext.likeablePerson.service.LikeablePersonService;
import com.ll.gramgram.boundedContext.member.entity.Member;
import com.ll.gramgram.boundedContext.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class LikeablePersonControllerTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private MemberService memberService;
    @Autowired
    private InstaMemberService instaMemberService;
    @Autowired
    private LikeablePersonService likeablePersonService;

    @Test
    @DisplayName("등록 폼(인스타 인증을 안해서 폼 대신 메세지)")
    @WithUserDetails("user1")
    void t001() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/likeablePerson/add"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(LikeablePersonController.class))
                .andExpect(handler().methodName("showAdd"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("""
                        먼저 본인의 인스타그램 아이디를 입력해주세요.
                        """.stripIndent().trim())))
        ;
    }

    @Test
    @DisplayName("등록 폼")
    @WithUserDetails("user2")
    void t002() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/likeablePerson/add"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(LikeablePersonController.class))
                .andExpect(handler().methodName("showAdd"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("""
                        <input type="text" name="username"
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="radio" name="attractiveTypeCode" value="1"
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="radio" name="attractiveTypeCode" value="2"
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="radio" name="attractiveTypeCode" value="3"
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="submit" value="추가"
                        """.stripIndent().trim())));
    }

    @Test
    @DisplayName("등록 폼 처리(user2가 user3에게 호감표시(외모))")
    @WithUserDetails("user2")
    void t003() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(post("/likeablePerson/add")
                        .with(csrf()) // CSRF 키 생성
                        .param("username", "insta_user3")
                        .param("attractiveTypeCode", "1")
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(LikeablePersonController.class))
                .andExpect(handler().methodName("add"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("등록 폼 처리(user2가 abcd에게 호감표시(외모), abcd는 아직 우리 서비스에 가입하지 않은상태)")
    @WithUserDetails("user2")
    void t004() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(post("/likeablePerson/add")
                        .with(csrf()) // CSRF 키 생성
                        .param("username", "abcd")
                        .param("attractiveTypeCode", "2")
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(LikeablePersonController.class))
                .andExpect(handler().methodName("add"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("호감목록")
    @WithUserDetails("user3")
    void t005() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/likeablePerson/list"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(LikeablePersonController.class))
                .andExpect(handler().methodName("showList"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("""
                        <span class="toInstaMember_username">insta_user4</span>
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <span class="toInstaMember_attractiveTypeDisplayName">외모</span>
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <span class="toInstaMember_username">insta_user100</span>
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <span class="toInstaMember_attractiveTypeDisplayName">성격</span>
                        """.stripIndent().trim())));
    }

    @Test
    @DisplayName("likeablePerson delete")
    @WithUserDetails("KAKAO__2733144890")
    void t006() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(
                        delete("/likeablePerson/4")
                                .with(csrf())
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(LikeablePersonController.class))
                .andExpect(handler().methodName("delete"))
                .andExpect(status().is3xxRedirection());

        RsData<LikeablePerson> likeablePersonRsData = likeablePersonService.findById(4L);
        assertThat(likeablePersonRsData.getData()).isNull();
    }

    @Test
    @DisplayName("likeablePerson delete not valid")
    @WithUserDetails("user4")
    void t007() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(
                        delete("/likeablePerson/4")
                                .with(csrf())
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(LikeablePersonController.class))
                .andExpect(handler().methodName("delete"))
                .andExpect(status().is4xxClientError());

        RsData<LikeablePerson> likeablePersonRsData = likeablePersonService.findById(4L);
        assertThat(likeablePersonRsData.getData()).isNotNull();
    }

    @Test
    @DisplayName("likeablePerson modify")
    @WithUserDetails("KAKAO__2733144890")
    void t008() throws Exception {
        Member fromMember = memberService.findByUsername("KAKAO__2733144890").orElse(null);
        Member toMember = memberService.findByUsername("user2").orElse(null);
        assertThat(fromMember).isNotNull();
        assertThat(toMember).isNotNull();

        InstaMember fromInstaMember = instaMemberService.findByUsername(fromMember.getInstaMember().getUsername()).orElse(null);
        InstaMember toInstaMember = instaMemberService.findByUsername(toMember.getInstaMember().getUsername()).orElse(null);
        assertThat(fromInstaMember).isNotNull();
        assertThat(toInstaMember).isNotNull();

        LikeablePerson likeablePerson = likeablePersonService.findByFromInstaMemberAndToInstaMember(fromInstaMember, toInstaMember).getData();
        int curAttractiveTypeCode = likeablePerson.getAttractiveTypeCode();

        // WHEN
        ResultActions resultActions = mvc
                .perform(post("/likeablePerson/modify/%d".formatted(likeablePerson.getId()))
                        .with(csrf()) // CSRF 키 생성
                        .param("attractiveTypeCode", String.valueOf((curAttractiveTypeCode + 2) % 3 + 1))
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(LikeablePersonController.class))
                .andExpect(handler().methodName("modify"))
                .andExpect(status().is3xxRedirection())
        ;
        assertThat(likeablePersonService.findByFromInstaMemberAndToInstaMember(fromInstaMember, toInstaMember).getData().getAttractiveTypeCode()).isEqualTo((curAttractiveTypeCode + 2) % 3 + 1);
    }

    @Test
    @DisplayName("fromMember must like toMember only once")
    @WithUserDetails("KAKAO__2733144890")
    void t009() throws Exception {
        Member fromMember = memberService.findByUsername("KAKAO__2733144890").orElse(null);
        Member toMember = memberService.findByUsername("user2").orElse(null);
        assertThat(fromMember).isNotNull();
        assertThat(toMember).isNotNull();

        InstaMember fromInstaMember = instaMemberService.findByUsername(fromMember.getInstaMember().getUsername()).orElse(null);
        InstaMember toInstaMember = instaMemberService.findByUsername(toMember.getInstaMember().getUsername()).orElse(null);
        assertThat(fromInstaMember).isNotNull();
        assertThat(toInstaMember).isNotNull();

        LikeablePerson likeablePerson = likeablePersonService.findByFromInstaMemberAndToInstaMember(fromInstaMember, toInstaMember).getData();
        assertThat(likeablePerson).isNotNull();

        long beforeAddCount = likeablePersonService.countByMember(fromInstaMember);
        // WHEN
        ResultActions resultActions = mvc
                .perform(post("/likeablePerson/add")
                        .with(csrf()) // CSRF 키 생성
                        .param("username", toInstaMember.getUsername())
                        .param("attractiveTypeCode", "1")
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(LikeablePersonController.class))
                .andExpect(handler().methodName("add"))
                .andExpect(status().is4xxClientError());

        assertThat(likeablePersonService.countByMember(fromInstaMember)).isEqualTo(beforeAddCount);
    }
}