package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.EnumNamingStrategies;
import com.fasterxml.jackson.databind.annotation.EnumNaming;

/**
 * Arcaea 谱面难度, 分为 Past, Present, Future, Beyond, Eternal 和 Inscribed (Divine Oblivion包特有).
 *
 * @author RikkaKawaii0612, HSP
 */
@EnumNaming(EnumNamingStrategies.SnakeCaseStrategy.class)
public enum Difficulty {
    PST("Past"),
    PRS("Present"),
    FTR("Future"),
    BYD("Beyond"),
    ETR("Eternal"),
    INS("Inscribed");

    private final String name;

    Difficulty(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @JsonCreator
    public static Difficulty fromString(String string) {
        for (Difficulty d : Difficulty.values()) {
            if (d.name().equalsIgnoreCase(string) || d.name.equalsIgnoreCase(string)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Difficulty '" + string + "' does not exist");
    }
}
