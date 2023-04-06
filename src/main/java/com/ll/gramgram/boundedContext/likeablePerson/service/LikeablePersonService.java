package com.ll.gramgram.boundedContext.likeablePerson.service;

import com.ll.gramgram.base.rsData.RsData;
import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import com.ll.gramgram.boundedContext.instaMember.service.InstaMemberService;
import com.ll.gramgram.boundedContext.likeablePerson.entity.AttractiveType;
import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import com.ll.gramgram.boundedContext.likeablePerson.repository.LikeablePersonRepository;
import com.ll.gramgram.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeablePersonService {
    private final LikeablePersonRepository likeablePersonRepository;
    private final InstaMemberService instaMemberService;

    @Transactional
    public RsData<LikeablePerson> like(Member member, String username, AttractiveType attractiveType) {
        if (!member.hasConnectedInstaMember()) {
            return RsData.of("F-2", "먼저 본인의 인스타그램 아이디를 입력해야 합니다.");
        }

        if (member.getInstaMember().getUsername().equals(username)) {
            return RsData.of("F-1", "본인을 호감상대로 등록할 수 없습니다.");
        }

        InstaMember toInstaMember = instaMemberService.findByUsernameOrCreate(username).getData();
        if (likeablePersonRepository.findByFromInstaMemberIdAndToInstaMemberId(member.getInstaMember().getId(), toInstaMember.getId()).isPresent()) {
            return RsData.of("F-1", "이미 등록한 호감상대 입니다.");
        }

        LikeablePerson likeablePerson = LikeablePerson
                .builder()
                .fromInstaMember(member.getInstaMember()) // 호감을 표시하는 사람의 인스타 멤버
                .fromInstaMemberUsername(member.getInstaMember().getUsername()) // 중요하지 않음
                .toInstaMember(toInstaMember) // 호감을 받는 사람의 인스타 멤버
                .toInstaMemberUsername(toInstaMember.getUsername()) // 중요하지 않음
                .attractiveType(attractiveType)
                .build();

        likeablePersonRepository.save(likeablePerson); // 저장

        return RsData.of("S-1", "입력하신 인스타유저(%s)를 호감상대로 등록되었습니다.".formatted(username), likeablePerson);
    }

    public List<LikeablePerson> findByFromInstaMemberId(Long fromInstaMemberId) {
        return likeablePersonRepository.findByFromInstaMemberId(fromInstaMemberId);
    }

    public RsData<LikeablePerson> findById(Long id) {
        Optional<LikeablePerson> likeablePerson = likeablePersonRepository.findById(id);
        return likeablePerson.map(RsData::successOf).orElseGet(() -> RsData.of("F-1", "해당하는 호감상대를 찾을 수 없습니다.", null));
    }

    @Transactional
    public RsData<Boolean> delete(LikeablePerson likeablePerson, InstaMember fromInstaMember) {
        if (!likeablePerson.getFromInstaMember().getId().equals(fromInstaMember.getId())) {
            return RsData.of("F-1", "호감상대를 삭제할 권한이 없습니다.", Boolean.FALSE);
        }

        likeablePersonRepository.delete(likeablePerson);
        return RsData.of("S-1", "호감상대를 삭제했습니다.", Boolean.TRUE);
    }

    // 해당 인스타 멤버의 호감상대 개수
    public Long countByMember(InstaMember fromInstaMember) {
        return likeablePersonRepository.countByFromInstaMemberId(fromInstaMember.getId());
    }

    public RsData<LikeablePerson> findByFromInstaMemberAndToInstaMember(InstaMember fromInstaMember, InstaMember toInstaMember) {
        return RsData.successOf(likeablePersonRepository.findByFromInstaMemberIdAndToInstaMemberId(fromInstaMember.getId(), toInstaMember.getId()).orElse(null));
    }

    @Transactional
    public RsData<LikeablePerson> modifyAttractive(Long likeableId, AttractiveType attractiveType) {
        LikeablePerson likeablePerson = likeablePersonRepository.findById(likeableId).orElse(null);
        if (likeablePerson == null) {
            return RsData.of("F-1", "유효한 호감표시가 아닙니다.");
        }

        // need setter or DTO
        likeablePerson = LikeablePerson
                .builder()
                .id(likeablePerson.getId())
                .createDate(likeablePerson.getCreateDate())
                .fromInstaMember(likeablePerson.getFromInstaMember())
                .fromInstaMemberUsername(likeablePerson.getFromInstaMemberUsername())
                .toInstaMember(likeablePerson.getToInstaMember())
                .toInstaMemberUsername(likeablePerson.getToInstaMemberUsername())
                .attractiveType(attractiveType) // actual change
                .build();

        likeablePersonRepository.save(likeablePerson);

        return RsData.of("S-1", "입력하신 인스타유저(%s)의 매력포인트를 변경했습니다.".formatted(likeablePerson.getToInstaMember()), likeablePerson);
    }

}
