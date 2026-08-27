package io.github.phiseecodyhsp.arcstory.res;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 资源路径. 可用于 {@link ResourceLoader}, 代表特定的一个资源文件.
 *
 * <p>分类和键必须遵循 <b>lower_snake_case</b>, 否则抛异常.
 *
 * @param category 资源分类
 * @param key 资源键
 *
 * @see ResourceLoader
 *
 * @author RikkaKawaii0612
 */
public record ResourceLocation(@JsonProperty String category, @JsonProperty String key) {

    private static final Pattern LOWER_SNAKE_CASE = Pattern.compile("^[a-z0-9]+(_[a-z0-9]+)*$");

    public ResourceLocation {
        if (!LOWER_SNAKE_CASE.matcher(category).matches()) {
            throw new IllegalArgumentException("Category must match lower_snake_case");
        }
        if (!LOWER_SNAKE_CASE.matcher(key).matches()) {
            throw new IllegalArgumentException("Key must match lower_snake_case");
        }
    }

    @JsonCreator
    public ResourceLocation(String location) {
        Objects.requireNonNull(location);

        int index = location.indexOf("/");
        if (index == -1) {
            throw new IllegalArgumentException("Resource Location must contains '/' for separating category and key");
        }
        String category = location.substring(0, index);
        String key = location.substring(1 + index);
        this(category, key);
    }

    @JsonIgnore
    public String getLocation() {
        return this.category() + "/" + this.key();
    }

    public static ResourceLocation image(String key) {
        return new ResourceLocation("images", key);
    }

    public static ResourceLocation audio(String key) {
        return new ResourceLocation("audios", key);
    }

    public static ResourceLocation font(String key) {
        return new ResourceLocation("fonts", key);
    }

    public static ResourceLocation story(String key) {
        return new ResourceLocation("stories", key);
    }

    public static ResourceLocation text(String key) {
        return new ResourceLocation("texts", key);
    }

    public static ResourceLocation chart(String key) {
        return new ResourceLocation("charts", key);
    }

    public static ResourceLocation partner(String key) {
        return new ResourceLocation("partners", key);
    }

    public static ResourceLocation storyRequirement(String key) {
        return new ResourceLocation("story_requirements", key);
    }
}
