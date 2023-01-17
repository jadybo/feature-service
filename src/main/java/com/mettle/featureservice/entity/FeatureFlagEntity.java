package com.mettle.featureservice.entity;

import com.mettle.featureservice.model.FeatureFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Class representing single Feature Flag Entity
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeatureFlagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean enabled;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public static FeatureFlagEntity fromPojo(FeatureFlag featureFlag) {

        return FeatureFlagEntity.builder()
                .id(featureFlag.id())
                .name(featureFlag.name())
                .enabled(featureFlag.enabled())
                .build();
    }

    public FeatureFlag toPojo() {

        return FeatureFlag.builder()
                .id(this.getId())
                .name(this.getName())
                .enabled(this.getEnabled())
                .build();
    }

}
