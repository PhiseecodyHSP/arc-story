package io.github.phiseecodyhsp.arcstory.model.story;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.phiseecodyhsp.arcstory.model.Partner;
import io.github.phiseecodyhsp.arcstory.res.ResourceLoader;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * 由 JSON 数据驱动的 Arcaea 故事.
 *
 * @author RikkaKawaii0612
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class Story {

    @JsonProperty
    private List<ResourceLocation> partnerLocations = new ArrayList<>();

    @JsonProperty
    private List<Paragraph> paragraphs = new ArrayList<>();

    public Story() {
    }

    public List<ResourceLocation> getPartnerLocations() {
        return this.partnerLocations;
    }

    public void setPartnerLocations(List<ResourceLocation> partnerLocations) {
        this.partnerLocations = partnerLocations;
    }

    public List<Partner> getPartners() {
        return this.partnerLocations.stream().map(ResourceLoader::loadPartner).toList();
    }

    public List<Paragraph> getParagraphs() {
        return this.paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
