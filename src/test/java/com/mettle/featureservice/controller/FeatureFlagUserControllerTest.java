package com.mettle.featureservice.controller;

import com.mettle.featureservice.controller.app.FeatureFlagUserController;
import com.mettle.featureservice.model.FeatureFlagUser;
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
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeatureFlagUserController.class)
@RunWith(SpringRunner.class)
public class FeatureFlagUserControllerTest {

    @Inject
    private MockMvc mockMvc;

    @MockBean
    FeatureFlagUserService featureFlagUserService;

    @Test
    public void getCallsServiceWithUserIdAndSerializesCorrectOutput() throws Exception {
        var now = LocalDateTime.now();

        var featureFlagUsers = Collections.singletonList(FeatureFlagUser.builder()
                .id(321L)
                .createdAt(now)
                .userId(123L)
                .featureFlagId(456L)
                .build());

        when(featureFlagUserService.findEnabledFlagsByUserId(eq(123L))).thenReturn(asList("ff1", "ff2"));
        this.mockMvc.perform(
                    get("/app/123/enabled-feature-flags").contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.*", hasSize(2)))

                .andExpect(jsonPath("$[0]").value("ff1"))
                .andExpect(jsonPath("$[1]").value("ff2")
                );

    }

}