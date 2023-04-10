package com.ll.gramgram.boundedContext.likeablePerson.controller;

import com.ll.gramgram.base.rq.Rq;
import com.ll.gramgram.base.rsData.RsData;
import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import com.ll.gramgram.boundedContext.likeablePerson.entity.AttractiveType;
import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import com.ll.gramgram.boundedContext.likeablePerson.service.LikeablePersonService;
import com.ll.gramgram.boundedContext.member.entity.Member;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/likeablePerson")
@RequiredArgsConstructor
public class LikeablePersonController {
    private final Rq rq;
    private final LikeablePersonService likeablePersonService;

    @GetMapping("/add")
    public String showAdd(Model model) {
        model.addAttribute("attractiveTypes", AttractiveType.values());

        return "usr/likeablePerson/add";
    }

    @AllArgsConstructor
    @Getter
    public static class AddForm {
        private final String username;
        private final int attractiveTypeCode;
    }

    @PostMapping("/add")
    public String add(@Valid AddForm addForm) {
        AttractiveType attractiveType = AttractiveType.findByCode(addForm.getAttractiveTypeCode());
        if (attractiveType == null) {
            // need to change
            return rq.historyBack(RsData.failOf(null));
        }

        RsData<LikeablePerson> createRsData = likeablePersonService.like(rq.getMember(), addForm.getUsername(), attractiveType);
        if (createRsData.isFail()) {
            return rq.historyBack(createRsData);
        }

        return rq.redirectWithMsg("/likeablePerson/list", createRsData);
    }

    @GetMapping("/list")
    public String showList(Model model) {
        InstaMember instaMember = rq.getMember().getInstaMember();

        // 인스타인증을 했는지 체크
        if (instaMember != null) {
            List<LikeablePerson> likeablePeople = instaMember.getFromLikeablePeople();
            model.addAttribute("likeablePeople", likeablePeople);
        }

        return "usr/likeablePerson/list";
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        Member member = rq.getMember();
        if (!member.hasConnectedInstaMember()) {
            return rq.redirectWithMsg("/likeablePerson/list", "먼저 본인의 인스타그램 아이디를 입력해야 합니다.");
        }

        RsData<LikeablePerson> findLikeablePersonRs = likeablePersonService.findById(id);
        if (findLikeablePersonRs.isFail()) {
            return rq.historyBack(findLikeablePersonRs.getMsg());
        }

        RsData<Boolean> deleteLikeablePersonRs = likeablePersonService.delete(member, findLikeablePersonRs.getData());
        if (deleteLikeablePersonRs.isFail()) {
            return rq.historyBack(deleteLikeablePersonRs.getMsg());
        }

        return rq.redirectWithMsg("/likeablePerson/list", deleteLikeablePersonRs.getMsg());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable Long id, Model model) {
        Member member = rq.getMember();
        if (!member.hasConnectedInstaMember()) {
            return rq.redirectWithMsg("/likeablePerson/list", "먼저 본인의 인스타그램 아이디를 입력해야 합니다.");
        }

        RsData<LikeablePerson> findLikeablePersonRs = likeablePersonService.findById(id);
        if (findLikeablePersonRs.isFail()) {
            return rq.redirectWithMsg("/likeablePerson/list", findLikeablePersonRs.getMsg());
        }

        LikeablePerson likeablePerson = findLikeablePersonRs.getData();
        if (!member.getInstaMember().getId().equals(likeablePerson.getFromInstaMember().getId())) {
            return rq.redirectWithMsg("/likeablePerson/list", "잘못된 접근입니다.");
        }

        model.addAttribute("likeablePerson", likeablePerson);
        model.addAttribute("attractiveTypes", AttractiveType.values());
        return "usr/likeablePerson/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable Long id, @Valid AddForm addForm) {
        AttractiveType attractiveType = AttractiveType.findByCode(addForm.getAttractiveTypeCode());
        if (attractiveType == null) {
            // need to change
            return rq.historyBack(RsData.failOf(null));
        }
        RsData<LikeablePerson> findLikeablePersonRs = likeablePersonService.findById(id);
        if (findLikeablePersonRs.isFail()) {
            return findLikeablePersonRs.getMsg();
        }

        LikeablePerson likeablePerson = findLikeablePersonRs.getData();
        Member member = rq.getMember();
        RsData<Boolean> canModRs = likeablePersonService.isYourLike(member, likeablePerson);
        if (canModRs.isFail()) {
            return canModRs.getMsg();
        }

        RsData<LikeablePerson> deleteLikeablePersonRs = likeablePersonService.modifyAttractive(likeablePerson, attractiveType);
        return rq.redirectWithMsg("/likeablePerson/list", deleteLikeablePersonRs.getMsg());
    }
}
