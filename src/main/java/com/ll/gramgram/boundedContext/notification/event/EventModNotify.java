package com.ll.gramgram.boundedContext.notification.event;

import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import lombok.Getter;

@Getter
public class EventModNotify {
    private final LikeablePerson likeablePerson;
    private final int oldAttractiveTypeCode;

    public EventModNotify(LikeablePerson likeablePerson, int oldAttractiveTypeCode) {
        this.likeablePerson = likeablePerson;
        this.oldAttractiveTypeCode = oldAttractiveTypeCode;
    }
}
