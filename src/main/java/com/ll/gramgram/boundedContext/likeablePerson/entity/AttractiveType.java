package com.ll.gramgram.boundedContext.likeablePerson.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AttractiveType {
    APPEARANCE(1, "외모"),
    ABILITY(2, "능력"),
    PERSONALITY(3, "성격");

    private final Integer code;
    private final String value;

    public static AttractiveType findByCode(Integer code) {
        return Arrays.stream(values())
                .filter(a -> a.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not Supported AttractiveType"));
    }
}
