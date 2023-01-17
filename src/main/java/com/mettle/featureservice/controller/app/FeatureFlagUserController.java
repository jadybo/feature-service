package com.mettle.featureservice.controller.app;

import com.mettle.featureservice.service.FeatureFlagUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Spring REST Controller for Feature Flags User API
 */
@RestController
public class FeatureFlagUserController {

    private FeatureFlagUserService featureFlagUserService;

    @Inject
    public FeatureFlagUserController(FeatureFlagUserService featureFlagUserService) {
        this.featureFlagUserService = featureFlagUserService;
    }

    /**
     * Returns a list of Feature Flags enabled for given user
     * @param userId for which to obtain Feature Flags
     * @return List of Feature Flags enabled for this user
     */
    //TODO: grab user ID from JWT token and remove userId path param here
    @GetMapping(value="/app/{userId}/enabled-feature-flags", produces = "application/json;charset=UTF-8")
    public List<String> listEnabledByUserId(@PathVariable Long userId) {
        return featureFlagUserService.findEnabledFlagsByUserId(userId);
    }

}