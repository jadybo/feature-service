package com.mettle.featureservice.service;

import com.mettle.featureservice.entity.FeatureFlagEntity;
import com.mettle.featureservice.model.FeatureFlag;
import com.mettle.featureservice.repository.FeatureFlagRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Service handling Feature Flags
 */
@Service
public class FeatureFlagService {

    private FeatureFlagRepository featureFlagRepository;

    @Inject
    public FeatureFlagService(FeatureFlagRepository featureFlagRepository) {
        this.featureFlagRepository = featureFlagRepository;
    }

    /**
     * Creates new Feature Flag in the DB
     * @param featureFlag Feature Flag to be created
     * @throws IllegalArgumentException when Feature Flag passed is null
     * @return newly created Feature Flag
     */
    public FeatureFlag create(FeatureFlag featureFlag) {
        if (featureFlag == null) {
            throw new IllegalArgumentException("Feature flag cannot be null!");
        }
        FeatureFlagEntity entity = FeatureFlagEntity.fromPojo(featureFlag);
        featureFlagRepository.save(entity);
        return entity.toPojo();
    }

}