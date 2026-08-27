package io.github.phiseecodyhsp.arcstory.ui.base;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

/**
 * Loading 界面的百叶窗开合音效类型.
 *
 * @author HSP
 */
public enum ShutterAudio {

    NORMAL(ResourceLocation.audio("shutter_close"), ResourceLocation.audio("shutter_open")),
    COURSE(ResourceLocation.audio("shutter_course_close"), ResourceLocation.audio("shutter_course_open")),
    ALT(ResourceLocation.audio("shutter_alt_close"), ResourceLocation.audio("shutter_alt_open"));

    private final ResourceLocation open;
    private final ResourceLocation close;

    ShutterAudio(ResourceLocation open, ResourceLocation close) {
        this.open = open;
        this.close = close;
    }

    public ResourceLocation getClose() {
        return close;
    }

    public ResourceLocation getOpen() {
        return open;
    }
}
