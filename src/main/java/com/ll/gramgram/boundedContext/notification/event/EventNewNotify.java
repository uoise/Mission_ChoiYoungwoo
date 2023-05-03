package com.ll.gramgram.boundedContext.notification.event;

import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventNewNotify extends ApplicationEvent {
    private final LikeablePerson likeablePerson;

    public EventNewNotify(Object source, LikeablePerson likeablePerson) {
        super(source);
        this.likeablePerson = likeablePerson;
    }
}
