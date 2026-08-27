package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * 由 JSON 数据驱动的存储故事解锁条件数据的类.
 *
 * @param chartLocation 谱面路径
 * @param partnerLocation 搭档路径
 *
 * @author HSP
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record StoryRequirement(@JsonProperty ResourceLocation chartLocation,
                               @JsonProperty @Nullable ResourceLocation partnerLocation) {

    public StoryRequirement {
        if (chartLocation == null) {
            throw new IllegalArgumentException("Chart cannot be null");
        }
    }
}
