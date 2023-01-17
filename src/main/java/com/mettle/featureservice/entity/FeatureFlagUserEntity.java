package com.mettle.featureservice.entity;

import com.mettle.featureservice.model.FeatureFlagUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Class representing User Feature Flag Entity
 */
@Table(name = "feature_flag_user")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeatureFlagUserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long featureFlagId;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public static FeatureFlagUserEntity fromPojo(FeatureFlagUser featureFlagUser) {

        return FeatureFlagUserEntity.builder()
                .id(featureFlagUser.id())
                .userId(featureFlagUser.userId())
                .featureFlagId(featureFlagUser.featureFlagId())
                .build();
    }

    public FeatureFlagUser toPojo() {

        return FeatureFlagUser.builder()
                .id(this.getId())
                .userId(this.getUserId())
                .createdAt(this.getCreatedAt())
                .featureFlagId(this.getFeatureFlagId())
                .build();
    }

}
