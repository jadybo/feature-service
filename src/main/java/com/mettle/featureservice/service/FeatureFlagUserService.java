package com.mettle.featureservice.service;

import com.mettle.featureservice.entity.FeatureFlagUserEntity;
import com.mettle.featureservice.model.FeatureFlagUser;
import com.mettle.featureservice.repository.FeatureFlagRepository;
import com.mettle.featureservice.repository.FeatureFlagUserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Service handling User Feature Flag
 */
@Service
public class FeatureFlagUserService {

    private FeatureFlagUserRepository featureFlagUserRepository;
    private FeatureFlagRepository featureFlagRepository;

    @Inject
    public FeatureFlagUserService(FeatureFlagUserRepository featureFlagUserRepository, FeatureFlagRepository featureFlagRepository) {
        this.featureFlagUserRepository = featureFlagUserRepository;
        this.featureFlagRepository = featureFlagRepository;
    }

    /**
     * Returns a list of Feature Flags enabled for given user
     * @param userId ID of user for which to return enabled Feature Flags
     * @return List of User Feature Flags for this user
     * @throws IllegalArgumentException when userId passed
     */
    public List<String> findEnabledFlagsByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("USER ID cannot be null!");
        }

        return featureFlagRepository.findEnabledFeatureFlagsByUserId(userId);
    }

    /**
     * Enables Feature Flag for given user
     * @param featureFlagUser Feature Flag to be enabled
     * @throws IllegalArgumentException when Feature Flag passed is null
     * @return newly created Feature Flag -> User assignment
     */
    @Transactional
    public FeatureFlagUser enableFeatureFlag(FeatureFlagUser featureFlagUser) {
        if (featureFlagUser == null) {
            throw new IllegalArgumentException("Feature Flag cannot be null!");
        }
        return featureFlagUserRepository.save(FeatureFlagUserEntity.fromPojo(featureFlagUser)).toPojo();
    }

}