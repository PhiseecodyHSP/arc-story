package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.phiseecodyhsp.arcstory.res.ResourceLoader;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.util.MathUtil;
import javafx.scene.image.Image;

/**
 * 由 JSON 数据驱动的 Arcaea 谱面基本信息.
 *
 * @author RikkaKawaii0612, HSP
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Chart(@JsonProperty String music,
                    @JsonProperty ResourceLocation musicLocation,
                    @JsonProperty String composer,
                    @JsonProperty double leftBpm,
                    @JsonProperty double rightBpm,
                    @JsonProperty Difficulty difficulty,
                    @JsonProperty double rating,
                    @JsonProperty ResourceLocation illustrationLocation,
                    @JsonProperty String illustrator,
                    @JsonProperty String noteDesigner,
                    @JsonProperty Paradigm paradigm) {

    public String getBpm() {
        // BPM 左右值相等时视为不变, 只显示单个数字
        return this.leftBpm == this.rightBpm ?
                MathUtil.doubleToString(this.leftBpm) :
                MathUtil.doubleToString(this.leftBpm) + "-" + MathUtil.doubleToString(this.rightBpm);
    }

    public String getLevel() {
        return MathUtil.ratingToLevel(this.rating());
    }

    public Image getIllustration() {
        return ResourceLoader.loadImage(this.illustrationLocation);
    }
}
