package com.mettle.featureservice.model;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Class representing single Feature Flag
 */
public record FeatureFlagUser (Long id, Long userId, Long featureFlagId, LocalDateTime createdAt) {

    @Builder
    public FeatureFlagUser {}

}
