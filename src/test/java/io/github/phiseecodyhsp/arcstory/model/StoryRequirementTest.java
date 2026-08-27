package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoryRequirementTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    @DisplayName("同时需求谱面和搭档的解锁条件定义与读取")
    void defaultConstructor_tutorialAndHikari() throws JsonProcessingException {
        String json =
                """
                    {
                        "chart_location": "charts/tutorial_pst",
                        "partner_location": "partners/hikari"
                    }
                """;
        StoryRequirement requirement = MAPPER.readValue(json, StoryRequirement.class);

        assertEquals("charts/tutorial_pst", requirement.chartLocation().getLocation());
        assertNotNull(requirement.partnerLocation());
        assertEquals("partners/hikari", requirement.partnerLocation().getLocation());
    }

    @Test
    @DisplayName("不需要搭档的解锁条件定义与读取")
    void defaultConstructor_noPartner() throws JsonProcessingException {
        String json =
                """
                    {
                        "chart_location": "charts/tutorial_pst",
                        "partner_location": null
                    }
                """;
        StoryRequirement requirement = MAPPER.readValue(json, StoryRequirement.class);

        assertEquals("charts/tutorial_pst", requirement.chartLocation().getLocation());
        assertNull(requirement.partnerLocation());
    }

    @Test
    @DisplayName("传入谱面为空时应抛出 ValueInstantiationException")
    void defaultConstructor_noChart_throwsVie() {
        String json =
                """
                    {
                        "chart_location": null,
                        "partner_location": "partners/hikari"
                    }
                """;
        assertThrows(ValueInstantiationException.class, () -> MAPPER.readValue(json, StoryRequirement.class));
    }
}
