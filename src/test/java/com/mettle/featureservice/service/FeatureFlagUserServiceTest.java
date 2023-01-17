package com.mettle.featureservice.service;

import com.mettle.featureservice.entity.FeatureFlagUserEntity;
import com.mettle.featureservice.model.FeatureFlagUser;
import com.mettle.featureservice.repository.FeatureFlagRepository;
import com.mettle.featureservice.repository.FeatureFlagUserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeatureFlagUserServiceTest {

    @Mock
    FeatureFlagUserRepository featureFlagUserRepository;

    @Mock
    FeatureFlagRepository featureFlagRepository;

    FeatureFlagUserService featureFlagUserService;

    @BeforeEach
    public void setUp() {
        featureFlagUserService = new FeatureFlagUserService(featureFlagUserRepository, featureFlagRepository);
    }

    @Test
    public void createsNewFeatureFlagUserAssignment() {
        var featureFlagUser = FeatureFlagUser.builder()
                .id(321L)
                .userId(123L)
                .featureFlagId(456L)
                .build();

        var expectedFeatureFlagEntity = FeatureFlagUserEntity.builder()
                .id(321L)
                .userId(123L)
                .featureFlagId(456L)
                .build();

        var returnedFeatureFlagEntity = FeatureFlagUserEntity.builder()
                .id(321L)
                .userId(123L)
                .featureFlagId(456L)
                .build();

        when(featureFlagUserRepository.save(expectedFeatureFlagEntity)).thenReturn(returnedFeatureFlagEntity);

        var actual = featureFlagUserService.enableFeatureFlag(featureFlagUser);

        assertThat(actual).isEqualTo(returnedFeatureFlagEntity.toPojo());
    }

    @Test
    public void throwsExceptionIfFFNull() {
        try {
            featureFlagUserService.enableFeatureFlag(null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            //all good here
        }
    }
}