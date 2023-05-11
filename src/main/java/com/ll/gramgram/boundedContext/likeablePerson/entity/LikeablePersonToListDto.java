package com.ll.gramgram.boundedContext.likeablePerson.entity;

import com.ll.gramgram.standard.util.Ut;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class LikeablePersonToListDto {
    private final Long id;
    private final Long fromInstaMemberId;
    private final Long toInstaMemberId;
    private final LocalDateTime createDate;
    private final String gender;
    private final Long fromInstaMemberLikesCount;
    private final int attractiveTypeCode;

    public String getJdenticon() {
        return Ut.hash.sha256(fromInstaMemberId + "_likes_" + toInstaMemberId);
    }

    public String getGenderDisplayName() {
        return gender.equals("W") ? "여성" : "남성";
    }

    public String getGenderDisplayNameWithIcon() {
        return switch (gender) {
            case "W" -> "<i class=\"fa-solid fa-person-dress\"></i>";
            default -> "<i class=\"fa-solid fa-person\"></i>";
        } + "&nbsp;" + getGenderDisplayName();
    }

    public String getAttractiveTypeDisplayName() {
        return switch (attractiveTypeCode) {
            case 1 -> "외모";
            case 2 -> "성격";
            default -> "능력";
        };
    }

    public String getAttractiveTypeDisplayNameWithIcon() {
        return switch (attractiveTypeCode) {
            case 1 -> "<i class=\"fa-solid fa-person-rays\"></i>";
            case 2 -> "<i class=\"fa-regular fa-face-smile\"></i>";
            default -> "<i class=\"fa-solid fa-people-roof\"></i>";
        } + "&nbsp;" + getAttractiveTypeDisplayName();
    }
}
