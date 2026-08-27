package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.EnumNamingStrategies;
import com.fasterxml.jackson.databind.annotation.EnumNaming;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

/**
 * 曲目的分侧, 分为光芒侧, 纷争侧, 消色侧, Lephon 侧 (Lucent Historia 包特有) 和 Dark Lephon 侧 (名称未定, Divine Oblivion包特有).
 *
 * @author RikkaKawaii0612, HSP
 */
@EnumNaming(EnumNamingStrategies.SnakeCaseStrategy.class)
public enum Paradigm {
    LIGHT(ResourceLocation.image("song_jacket_light")),
    CONFLICT(ResourceLocation.image("song_jacket_dark")),
    ACHROMIC(ResourceLocation.image("song_jacket_colorless")),
    LEPHON(ResourceLocation.image("song_jacket_lephon")),
    DARK_LEPHON(ResourceLocation.image("song_jacket_dark_lephon"));

    private final ResourceLocation jacket;

    Paradigm(ResourceLocation imageLocation) {
        this.jacket = imageLocation;
    }

    public ResourceLocation getJacket() {
        return this.jacket;
    }

    @JsonCreator
    public static Paradigm fromString(String string) {
        for (Paradigm p : Paradigm.values()) {
            if (p.name().equalsIgnoreCase(string)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Paradigm '" + string + "' does not exist");
    }
}
