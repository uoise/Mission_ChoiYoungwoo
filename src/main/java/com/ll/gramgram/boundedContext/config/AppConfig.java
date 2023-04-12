package com.ll.gramgram.boundedContext.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Getter
    private static Long likeablePersonMax;

    @Value("${constraints.likeable_person.max-count}")
    public void setLikeablePersonMax(long likeablePersonMax) {
        AppConfig.likeablePersonMax = likeablePersonMax;
    }
}
