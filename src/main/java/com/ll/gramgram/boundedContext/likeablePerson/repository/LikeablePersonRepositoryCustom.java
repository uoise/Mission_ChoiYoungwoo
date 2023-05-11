package com.ll.gramgram.boundedContext.likeablePerson.repository;

import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePersonToListDto;

import java.util.List;
import java.util.Optional;

public interface LikeablePersonRepositoryCustom {
    Optional<LikeablePerson> findQslByFromInstaMemberIdAndToInstaMember_username(long fromInstaMemberId, String toInstaMemberUsername);

    List<LikeablePersonToListDto> searchLikeablePerson(long toInstaMemberId, String gender, Integer attractiveTypeCode, Integer sortCode);
}
