package com.ll.gramgram.base.event;

import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import lombok.Getter;

@Getter
public class EventAfterModifyAttractiveType {
    private final LikeablePerson likeablePerson;
    private final int oldAttractiveTypeCode;
    private final int newAttractiveTypeCode;

    public EventAfterModifyAttractiveType(LikeablePerson likeablePerson, int oldAttractiveTypeCode, int newAttractiveTypeCode) {
        this.likeablePerson = likeablePerson;
        this.oldAttractiveTypeCode = oldAttractiveTypeCode;
        this.newAttractiveTypeCode = newAttractiveTypeCode;
    }
}
