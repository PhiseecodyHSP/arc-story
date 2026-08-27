package io.github.phiseecodyhsp.arcstory.model.story;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoryTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void defaultConstructor_defaultValues() {
        Story story = new Story();
        assertNotNull(story.getPartnerLocations());
        assertNotNull(story.getPartners());
        assertNotNull(story.getParagraphs());
        assertEquals(ArrayList.class, story.getParagraphs().getClass());
        assertTrue(story.getParagraphs().isEmpty());
    }

    @Test
    void setParagraphs_getParagraphs_roundTrip() {
        Story story = new Story();
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.TEXT, ResourceLocation.text("test_text")));
        story.setParagraphs(paragraphs);

        assertEquals(1, story.getParagraphs().size());
        assertEquals(ParagraphType.TEXT, story.getParagraphs().getFirst().type());
    }

    @Test
    void deserialize_parsesSnakeCaseJson() throws Exception {
        String json = """
                {
                  "partner_locations": []},
                  "paragraphs": [
                    {"type": "text", "location": {"category": "texts", "key": "test_text"}},
                    {"type": "cg", "location": {"category": "images", "key": "test_img"}}
                  ]
                }""";

        Story story = MAPPER.readValue(json, Story.class);

        assertEquals(2, story.getParagraphs().size());
        assertEquals(ParagraphType.TEXT, story.getParagraphs().getFirst().type());
        assertEquals("texts", story.getParagraphs().getFirst().location().category());
        assertEquals("test_text", story.getParagraphs().getFirst().location().key());
        assertEquals(ParagraphType.CG, story.getParagraphs().get(1).type());
    }

    @Test
    void deserialize_handlesEmptyParagraphs() throws Exception {
        String json = """
                {"partner_locations": [], "paragraphs": []}""";

        Story story = MAPPER.readValue(json, Story.class);

        assertNotNull(story.getParagraphs());
        assertTrue(story.getParagraphs().isEmpty());
    }

    @Test
    void deserialize_ignoresUnknownProperties() throws Exception {
        String json = """
                {
                  "partner_locations": [],
                  "paragraphs": [],
                  "extra_field": "should be ignored",
                  "another_one": 42
                }""";

        Story story = MAPPER.readValue(json, Story.class);
    }

    @Test
    void serializeDeserialize_roundTrip() throws Exception {
        Story original = new Story();
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.CG,
                new ResourceLocation("images", "test_img")));
        original.setParagraphs(paragraphs);

        String json = MAPPER.writeValueAsString(original);
        Story restored = MAPPER.readValue(json, Story.class);

        assertEquals(1, restored.getParagraphs().size());
        assertEquals(ParagraphType.CG, restored.getParagraphs().getFirst().type());
        assertEquals("images", restored.getParagraphs().getFirst().location().category());
        assertEquals("test_img", restored.getParagraphs().getFirst().location().key());
    }
}
