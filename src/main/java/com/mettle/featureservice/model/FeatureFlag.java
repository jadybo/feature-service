package com.mettle.featureservice.model;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Class representing a Feature Flag
 */
public record FeatureFlag (Long id, String name, Boolean enabled, LocalDateTime updatedAt) {

    @Builder
    public FeatureFlag {}

}
