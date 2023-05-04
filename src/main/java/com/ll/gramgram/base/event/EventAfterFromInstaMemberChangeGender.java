package com.ll.gramgram.base.event;

import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import lombok.Getter;

@Getter
public class EventAfterFromInstaMemberChangeGender {
    private final InstaMember instaMember;
    private final String oldGender;

    public EventAfterFromInstaMemberChangeGender(InstaMember instaMember, String oldGender) {
        this.instaMember = instaMember;
        this.oldGender = oldGender;
    }
}
