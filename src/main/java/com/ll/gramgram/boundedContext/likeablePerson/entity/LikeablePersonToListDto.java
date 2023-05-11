package com.ll.gramgram.boundedContext.likeablePerson.entity;

import com.ll.gramgram.standard.util.Ut;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class LikeablePersonToListDto {
    private final Long fromInstaMemberId;
    private final Long toInstaMemberId;
    private final String jdenticon;
    private final LocalDateTime createDate;
    private final String genderDisplayName;
    private final String genderDisplayNameWithIcon;
    private final String attractiveTypeDisplayName;
    private final String attractiveTypeDisplayNameWithIcon;

    public LikeablePersonToListDto(Long fromInstaMemberId, Long toInstaMemberId, LocalDateTime createDate, String gender, Integer attractiveTypeCode) {
        this.fromInstaMemberId = fromInstaMemberId;
        this.toInstaMemberId = toInstaMemberId;
        this.jdenticon = Ut.hash.sha256(fromInstaMemberId + "_likes_" + toInstaMemberId);
        this.createDate = createDate;

        this.genderDisplayName = gender.equals("W") ? "여성" : "남성";
        this.genderDisplayNameWithIcon = switch (gender) {
            case "W" -> "<i class=\"fa-solid fa-person-dress\"></i>";
            default -> "<i class=\"fa-solid fa-person\"></i>";
        } + "&nbsp;" + getGenderDisplayName();

        this.attractiveTypeDisplayName = switch (attractiveTypeCode) {
            case 1 -> "외모";
            case 2 -> "성격";
            default -> "능력";
        };
        this.attractiveTypeDisplayNameWithIcon = switch (attractiveTypeCode) {
            case 1 -> "<i class=\"fa-solid fa-person-rays\"></i>";
            case 2 -> "<i class=\"fa-regular fa-face-smile\"></i>";
            default -> "<i class=\"fa-solid fa-people-roof\"></i>";
        } + "&nbsp;" + getAttractiveTypeDisplayName();
    }
}
