package com.mettle.featureservice.repository;

import com.mettle.featureservice.entity.FeatureFlagEntity;
import com.mettle.featureservice.entity.FeatureFlagUserEntity;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FeatureFlagRepositoryTest {

    @Autowired
    private FeatureFlagRepository featureFlagRepository;

    @Autowired
    private FeatureFlagUserRepository featureFlagUserRepository;

    @Test
    public void rejectsDuplicatedName() {

        var featureFlag = FeatureFlagEntity.builder()
                .name("some name")
                .enabled(Boolean.TRUE)
                .build();

        var featureFlag2 = FeatureFlagEntity.builder()
                .name("some name")
                .enabled(Boolean.FALSE)
                .build();

        featureFlagRepository.save(featureFlag);

        try {
            featureFlagRepository.save(featureFlag2);
            Assert.fail();
        } catch (DataIntegrityViolationException dive) {
            //all good here
        }
    }

    @Test
    public void retrievesEnabledFeatureFlagsForUser() {

        var globallyEnabledFFSt = FeatureFlagEntity.builder()
                .name("globally enabled standalone flag")
                .enabled(Boolean.TRUE)
                .build();

        var globallyDisabledFFSt = FeatureFlagEntity.builder()
                .name("globally disabled standalone flag")
                .enabled(Boolean.FALSE)
                .build();

        var globallyEnabledFFOv = FeatureFlagEntity.builder()
                .name("globally enabled overrideable flag")
                .enabled(Boolean.TRUE)
                .build();

        var globallyDisabledFFOv = FeatureFlagEntity.builder()
                .name("globally disabled overrideable flag")
                .enabled(Boolean.FALSE)
                .build();

        featureFlagRepository.save(globallyEnabledFFSt);
        featureFlagRepository.save(globallyDisabledFFSt);
        featureFlagRepository.save(globallyEnabledFFOv);
        featureFlagRepository.save(globallyDisabledFFOv);

        var userEnabledGEOverride = FeatureFlagUserEntity.builder()
                .userId(123L)
                .featureFlagId(globallyEnabledFFOv.getId())
                .build();

        var userEnabledGDOverride = FeatureFlagUserEntity.builder()
                .userId(123L)
                .featureFlagId(globallyDisabledFFOv.getId())
                .build();

        featureFlagUserRepository.save(userEnabledGEOverride);
        featureFlagUserRepository.save(userEnabledGDOverride);

        List<String> enabledFeatureFlagsByUserId = featureFlagRepository.findEnabledFeatureFlagsByUserId(123L);

        Assertions.assertThat(enabledFeatureFlagsByUserId).hasSize(3);
        Assertions.assertThat(enabledFeatureFlagsByUserId).containsExactlyInAnyOrder(
                "globally enabled standalone flag",
                "globally enabled overrideable flag",
                "globally disabled overrideable flag");
    }

}