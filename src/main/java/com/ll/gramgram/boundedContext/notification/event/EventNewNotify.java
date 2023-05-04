package com.ll.gramgram.boundedContext.notification.event;

import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import lombok.Getter;

@Getter
public class EventNewNotify {
    private final LikeablePerson likeablePerson;

    public EventNewNotify(LikeablePerson likeablePerson) {
        this.likeablePerson = likeablePerson;
    }
}
