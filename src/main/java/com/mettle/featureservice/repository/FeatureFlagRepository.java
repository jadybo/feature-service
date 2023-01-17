package com.mettle.featureservice.repository;

import com.mettle.featureservice.entity.FeatureFlagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeatureFlagRepository extends JpaRepository<FeatureFlagEntity, Long> {

    //Note: Semantics is - if flag overridden on user level - this means it's always ON for this user.
    //DELETE the FeatureFlag->User association to disable feature flag for this user (will be disabled only if parent FF is still disabled too)
    @Query("select DISTINCT(fe.name) FROM FeatureFlagEntity fe " +
            "where " +
            "fe.enabled = TRUE " +
            "or " +
            "fe.id IN (select ffu.featureFlagId from FeatureFlagUserEntity ffu WHERE ffu.userId = :userId) ")
    List<String> findEnabledFeatureFlagsByUserId(@Param("userId") Long userId);

}
