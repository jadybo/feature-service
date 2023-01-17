package com.mettle.featureservice.controller.admin;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Class representing Create Feature Flag Request
 */

record CreateFeatureFlagRequest(Boolean enabled, @NotNull @NotBlank String name) {}
