package com.ll.gramgram.boundedContext.likeablePerson.util;

import com.ll.gramgram.boundedContext.likeablePerson.entity.AttractiveType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter
public class AttractiveTypeConverter implements AttributeConverter<com.ll.gramgram.boundedContext.likeablePerson.entity.AttractiveType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AttractiveType attribute) {
        return attribute.getCode();
    }

    @Override
    public AttractiveType convertToEntityAttribute(Integer dbData) {
        return AttractiveType.findByCode(dbData);
    }
}
