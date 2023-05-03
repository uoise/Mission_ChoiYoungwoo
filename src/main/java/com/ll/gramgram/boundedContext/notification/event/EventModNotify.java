package com.ll.gramgram.boundedContext.notification.event;

import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventModNotify extends ApplicationEvent {
    private final LikeablePerson likeablePerson;
    private final int oldAttractiveTypeCode;

    public EventModNotify(Object source, LikeablePerson likeablePerson, int oldAttractiveTypeCode) {
        super(source);
        this.likeablePerson = likeablePerson;
        this.oldAttractiveTypeCode = oldAttractiveTypeCode;
    }
}
