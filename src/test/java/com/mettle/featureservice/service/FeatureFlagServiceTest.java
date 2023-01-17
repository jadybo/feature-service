package com.mettle.featureservice.service;

import com.mettle.featureservice.entity.FeatureFlagEntity;
import com.mettle.featureservice.model.FeatureFlag;
import com.mettle.featureservice.repository.FeatureFlagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FeatureFlagServiceTest {

    @Mock
    FeatureFlagRepository featureFlagRepository;

    FeatureFlagService featureFlagService;

    @BeforeEach
    public void setUp() {
        featureFlagService = new FeatureFlagService(featureFlagRepository);
    }

    @Test
    public void createsNewFeatureFlag() {
        var featureFlag = FeatureFlag.builder()
                .name("some name")
                .enabled(Boolean.FALSE)
                .build();

        var expectedFeatureFlagEntity = FeatureFlagEntity.builder()
                .name("some name")
                .enabled(Boolean.FALSE)
                .build();

        var actual = featureFlagService.create(featureFlag);

        verify(featureFlagRepository).save(expectedFeatureFlagEntity);

        assertThat(actual).isEqualTo(featureFlag);
    }

}