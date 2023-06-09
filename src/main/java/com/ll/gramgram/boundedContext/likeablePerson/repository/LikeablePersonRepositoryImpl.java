package com.ll.gramgram.boundedContext.likeablePerson.repository;

import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePersonToListDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.ll.gramgram.boundedContext.likeablePerson.entity.QLikeablePerson.likeablePerson;

@RequiredArgsConstructor
public class LikeablePersonRepositoryImpl implements LikeablePersonRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<LikeablePerson> findQslByFromInstaMemberIdAndToInstaMember_username(long fromInstaMemberId, String toInstaMemberUsername) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(likeablePerson)
                        .where(
                                likeablePerson.fromInstaMember.id.eq(fromInstaMemberId)
                                        .and(
                                                likeablePerson.toInstaMember.username.eq(toInstaMemberUsername)
                                        )
                        )
                        .fetchOne()
        );
    }

    @Override
    public List<LikeablePersonToListDto> searchLikeablePerson(long toInstaMemberId, String gender, Integer attractiveTypeCode, Integer sortCode) {
        return jpaQueryFactory
                .select(Projections.constructor(LikeablePersonToListDto.class,
                                likeablePerson.id,
                                likeablePerson.fromInstaMember.id,
                                likeablePerson.toInstaMember.id,
                                likeablePerson.createDate,
                                likeablePerson.fromInstaMember.gender,
                                likeablePerson.fromInstaMember.likesCount,
                                likeablePerson.attractiveTypeCode
                        )
                )
                .from(likeablePerson)
                .where(
                        likeablePerson.toInstaMember.id.eq(toInstaMemberId)
                                .and(eqGender(gender))
                                .and(eqAtt(attractiveTypeCode))
                )
                .orderBy(orderSelector(sortCode))
                .fetch();
    }

    private BooleanExpression eqGender(String gender) {
        if (gender == null || gender.equals("U")) return null;
        return likeablePerson.fromInstaMember.gender.eq(gender);
    }

    private BooleanExpression eqAtt(Integer attractiveTypeCode) {
        if (attractiveTypeCode == null || attractiveTypeCode == 0) return null;
        return likeablePerson.attractiveTypeCode.eq(attractiveTypeCode);
    }

    private OrderSpecifier<?> orderSelector(Integer sortCode) {
        return switch (sortCode) {
            case 2 -> likeablePerson.id.asc();
            case 3 -> likeablePerson.fromInstaMember.likesCount.desc();
            case 4 -> likeablePerson.fromInstaMember.likesCount.asc();
            case 5 -> likeablePerson.fromInstaMember.gender.desc();
            case 6 -> likeablePerson.attractiveTypeCode.asc();
            default -> likeablePerson.id.desc();
        };
    }
}
