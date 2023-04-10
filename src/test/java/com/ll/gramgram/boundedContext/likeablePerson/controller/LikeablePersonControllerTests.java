package com.ll.gramgram.boundedContext.likeablePerson.controller;

import com.ll.gramgram.base.rsData.RsData;
import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import com.ll.gramgram.boundedContext.instaMember.service.InstaMemberService;
import com.ll.gramgram.boundedContext.likeablePerson.entity.AttractiveType;
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
                        <span class="toInstaMember_attractiveType_value">외모</span>
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <span class="toInstaMember_username">insta_user100</span>
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <span class="toInstaMember_attractiveType_value">능력</span>
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

        LikeablePerson likeablePerson = likeablePersonService.findByFromAndToInstaMember(fromInstaMember, toInstaMember).getData();
        AttractiveType curAttractiveType = likeablePerson.getAttractiveType();
        AttractiveType nxtAttractiveType = AttractiveType.findByCode((curAttractiveType.getCode() + 2) % 3 + 1);
        assertThat(nxtAttractiveType).isNotNull();

        // WHEN
        ResultActions resultActions = mvc
                .perform(post("/likeablePerson/modify/%d".formatted(likeablePerson.getId()))
                        .with(csrf()) // CSRF 키 생성
                        .param("attractiveTypeCode", String.valueOf(nxtAttractiveType.getCode()))
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(LikeablePersonController.class))
                .andExpect(handler().methodName("modify"))
                .andExpect(status().is3xxRedirection())
        ;
        assertThat(likeablePersonService.findByFromAndToInstaMember(fromInstaMember, toInstaMember).getData().getAttractiveType()).isEqualTo(nxtAttractiveType);
    }

    @Test
    @DisplayName("add method can modify exist likeablePerson")
    @WithUserDetails("KAKAO__2733144890")
    void t009() throws Exception {
        Member fromMember = memberService.findByUsername("KAKAO__2733144890").orElseThrow();
        Member toMember = memberService.findByUsername("user2").orElseThrow();

        InstaMember fromInstaMember = instaMemberService.findByUsername(fromMember.getInstaMember().getUsername()).orElseThrow();
        InstaMember toInstaMember = instaMemberService.findByUsername(toMember.getInstaMember().getUsername()).orElseThrow();

        LikeablePerson likeablePerson = likeablePersonService.findByFromAndToInstaMember(fromInstaMember, toInstaMember).getData();
        assertThat(likeablePerson).isNotNull();

        AttractiveType newAttractiveType = AttractiveType.findByCode((likeablePerson.getAttractiveType().getCode() + 1) % 3 + 1);
        assertThat(newAttractiveType).isNotNull();

        // WHEN
        ResultActions resultActions = mvc
                .perform(post("/likeablePerson/add")
                        .with(csrf()) // CSRF 키 생성
                        .param("username", toInstaMember.getUsername())
                        .param("attractiveTypeCode", String.valueOf(newAttractiveType.getCode()))
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(LikeablePersonController.class))
                .andExpect(handler().methodName("add"))
                .andExpect(status().is3xxRedirection());

        assertThat(likeablePersonService.findByFromAndToInstaMember(fromInstaMember, toInstaMember)
                .getData()
                .getAttractiveType()
        ).isEqualTo(newAttractiveType);
    }

    @Test
    @DisplayName("member must like less than LIMIT(11)")
    @WithUserDetails("KAKAO__2733144890")
    void t010() throws Exception {
        Member fromMember = memberService.findByUsername("KAKAO__2733144890").orElseThrow();
        InstaMember fromInstaMember = instaMemberService.findByUsername(fromMember.getInstaMember().getUsername()).orElseThrow();

        Long likeableCount = likeablePersonService.countByMember(fromInstaMember);

        String testInstaUsername = "guess_who";
        AttractiveType newAttractiveType = AttractiveType.ABILITY;

        // WHEN
        ResultActions resultActions = mvc
                .perform(post("/likeablePerson/add")
                        .with(csrf()) // CSRF 키 생성
                        .param("username", testInstaUsername)
                        .param("attractiveTypeCode", String.valueOf(newAttractiveType.getCode()))
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(LikeablePersonController.class))
                .andExpect(handler().methodName("add"))
                .andExpect(status().is4xxClientError());

        assertThat(likeablePersonService.countByMember(fromInstaMember)).isEqualTo(likeableCount);
    }
}