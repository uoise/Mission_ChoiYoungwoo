package com.ll.gramgram.base.security.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class OAuth2UserProviderConverter implements AttributeConverter<AuthProvider, String> {
    @Override
    public String convertToDatabaseColumn(AuthProvider authProvider) {
        return authProvider.getValue();
    }

    @Override
    public AuthProvider convertToEntityAttribute(String dbData) {
        return AuthProvider.of(dbData);
    }
}
