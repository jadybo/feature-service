package com.mettle.featureservice.repository;

import com.mettle.featureservice.entity.FeatureFlagEntity;
import com.mettle.featureservice.entity.FeatureFlagUserEntity;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FeatureFlagUserRepositoryTest {

    @Autowired
    private FeatureFlagUserRepository featureFlagUserRepository;

    @Autowired
    private FeatureFlagRepository featureFlagRepository;

    @Test
    public void rejectsDuplicateFeatureToUserAssignment() {

        var globalLevelFF = FeatureFlagEntity.builder()
                .name("globally disabled overrideable flag")
                .enabled(Boolean.FALSE)
                .build();

        featureFlagRepository.save(globalLevelFF);

        var userLevelFlag1 = FeatureFlagUserEntity.builder()
                .userId(123L)
                .featureFlagId(globalLevelFF.getId())
                .build();

        var userLevelFlag2 = FeatureFlagUserEntity.builder()
                .userId(123L)
                .featureFlagId(globalLevelFF.getId())
                .build();


        featureFlagUserRepository.save(userLevelFlag1);

        try {
            featureFlagUserRepository.save(userLevelFlag2);
            Assert.fail();
        } catch (DataIntegrityViolationException dive) {
            //all good here
        }
    }

}