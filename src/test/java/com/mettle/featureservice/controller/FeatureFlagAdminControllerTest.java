package com.mettle.featureservice.controller;

import com.mettle.featureservice.controller.admin.FeatureFlagAdminController;
import com.mettle.featureservice.model.FeatureFlag;
import com.mettle.featureservice.model.FeatureFlagUser;
import com.mettle.featureservice.service.FeatureFlagService;
import com.mettle.featureservice.service.FeatureFlagUserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeatureFlagAdminController.class)
@RunWith(SpringRunner.class)
public class FeatureFlagAdminControllerTest {

    @Inject
    private MockMvc mockMvc;

    @MockBean
    FeatureFlagService featureFlagService;

    @MockBean
    FeatureFlagUserService featureFlagUserService;

    @Test
    public void postNewFeatureFlagCallsService() throws Exception {
        var expectedFeatureFlag = FeatureFlag.builder()
                .name("some name")
                .enabled(Boolean.FALSE)
                .build();

        var returnedFeatureFlag = FeatureFlag.builder()
                .id(321L)
                .name("some name")
                .enabled(Boolean.FALSE)
                .build();

        when(featureFlagService.create(expectedFeatureFlag)).thenReturn(returnedFeatureFlag);

        this.mockMvc.perform(
                        post("/admin/feature-flags").content(" { \"name\": \"some name\" } ").contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(321L))
                .andExpect(jsonPath("$.name").value("some name"))
                .andExpect(jsonPath("$.enabled").value(Boolean.FALSE)
                );

    }

    @Test
    public void enablesFeatureFlagForUser() throws Exception {
        var expectedFeatureFlagUser = FeatureFlagUser.builder()
                .userId(123L)
                .featureFlagId(456L)
                .build();

        var now = LocalDateTime.now();
        var returnedFeatureFlagUser = FeatureFlagUser.builder()
                .id(321L)
                .createdAt(now)
                .featureFlagId(456L)
                .userId(123L)
                .build();

        when(featureFlagUserService.enableFeatureFlag(eq(expectedFeatureFlagUser))).thenReturn(returnedFeatureFlagUser);

        this.mockMvc.perform(
                        post("/admin/feature-flags/456/users/123").contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value("321"))
                .andExpect(jsonPath("$.userId").value("123"))
                .andExpect(jsonPath("$.featureFlagId").value("456"))
                .andExpect(jsonPath("$.createdAt").value(DateTimeFormatter.ISO_DATE_TIME.format(now))
                );

    }


}