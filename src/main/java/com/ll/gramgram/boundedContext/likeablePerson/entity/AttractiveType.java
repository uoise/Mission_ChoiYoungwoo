package com.ll.gramgram.boundedContext.likeablePerson.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum AttractiveType {
    APPEARANCE(1, "외모"),
    ABILITY(2, "능력"),
    PERSONALITY(3, "성격");

    private final Integer code;
    private final String value;

    public static AttractiveType findByCode(Integer code) {
        for (AttractiveType attractiveType : values()) {
            if (attractiveType.code.equals(code)) return attractiveType;
        }
        return null;
    }
}

