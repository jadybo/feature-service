package com.mettle.featureservice.controller.admin;

import com.mettle.featureservice.model.FeatureFlag;
import com.mettle.featureservice.model.FeatureFlagUser;
import com.mettle.featureservice.service.FeatureFlagService;
import com.mettle.featureservice.service.FeatureFlagUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Spring REST Controller for Feature Flags Admin API
 */
@RestController
public class FeatureFlagAdminController {

    private FeatureFlagService featureFlagService;
    private FeatureFlagUserService featureFlagUserService;

    @Inject
    public FeatureFlagAdminController(FeatureFlagService featureFlagService, FeatureFlagUserService featureFlagUserService) {
        this.featureFlagService = featureFlagService;
        this.featureFlagUserService = featureFlagUserService;
    }

    @PostMapping(value="/admin/feature-flags", consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    public FeatureFlag createFeatureFlag(@Valid @RequestBody CreateFeatureFlagRequest createFeatureFlagRequest) {
        return featureFlagService.create(FeatureFlag.builder().name(createFeatureFlagRequest.name()).enabled(Boolean.FALSE).build());
    }

    /**
     * Enable Feature Flag for given user
     * @param userId for which to register this Feature Flag
     * @return newly enabled Feature Flag assignment
     */
    @PostMapping(value="/admin/feature-flags/{id}/users/{userId}", produces = "application/json;charset=UTF-8")
    public FeatureFlagUser enableFeatureFlag(@PathVariable Long userId, @PathVariable Long id) {
        FeatureFlagUser featureFlagUser = FeatureFlagUser.builder()
                .userId(userId)
                .featureFlagId(id)
                .build();

        return featureFlagUserService.enableFeatureFlag(featureFlagUser);
    }


}