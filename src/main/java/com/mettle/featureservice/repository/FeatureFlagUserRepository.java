package com.mettle.featureservice.repository;

import com.mettle.featureservice.entity.FeatureFlagUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureFlagUserRepository extends JpaRepository<FeatureFlagUserEntity, Long> {

}
