package com.ll.gramgram.boundedContext.likeablePerson.repository;

import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import com.querydsl.core.types.OrderSpecifier;
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
    public List<LikeablePerson> searchLikeablePerson(long toInstaMemberId, String gender, Integer attractiveTypeCode, Integer sortCode) {
        return jpaQueryFactory
                .selectFrom(likeablePerson)
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
        return likeablePerson.toInstaMember.gender.eq(gender);
    }

    private BooleanExpression eqAtt(Integer attractiveTypeCode) {
        if (attractiveTypeCode == null || attractiveTypeCode == 0) return null;
        return likeablePerson.attractiveTypeCode.eq(attractiveTypeCode);
    }

    private OrderSpecifier<?> orderSelector(Integer sortCode) {
        return switch (sortCode) {
            case 2 -> likeablePerson.createDate.asc();
            case 3 -> likeablePerson.fromInstaMember.toLikeablePeople.size().desc();
            case 4 -> likeablePerson.fromInstaMember.toLikeablePeople.size().asc();
            case 5 -> likeablePerson.fromInstaMember.gender.desc();
            case 6 -> likeablePerson.attractiveTypeCode.asc();
            default -> likeablePerson.createDate.desc();
        };
    }
}
