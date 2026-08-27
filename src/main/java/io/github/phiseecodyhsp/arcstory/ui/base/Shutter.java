package io.github.phiseecodyhsp.arcstory.ui.base;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

/**
 * Loading 界面的百叶窗类型.
 *
 * @author HSP
 */
public enum Shutter {

    NORMAL(ResourceLocation.image("shutter_l"), ResourceLocation.image("shutter_r"), ShutterAudio.NORMAL),
    WITH(ResourceLocation.image("shutter_withoverlay_l"), ResourceLocation.image("shutter_withoverlay_r"), ShutterAudio.COURSE),
    GRIEVOUS(ResourceLocation.image("shutter_grievouslady_l"), ResourceLocation.image("shutter_grievouslady_r"), ShutterAudio.ALT),
    FRACTURE(ResourceLocation.image("shutter_fractureray_l"), ResourceLocation.image("shutter_fractureray_r"), ShutterAudio.ALT),
    TEMPESTISSIMO(ResourceLocation.image("shutter_tempestissimo_l"), ResourceLocation.image("shutter_tempestissimo_r"), ShutterAudio.ALT),
    FINALE(ResourceLocation.image("shutter_finale_l"), ResourceLocation.image("shutter_finale_r"), ShutterAudio.ALT),
    ARGHENA(ResourceLocation.image("shutter_arghena_l"), ResourceLocation.image("shutter_arghena_r"), ShutterAudio.ALT),
    LEPHON(ResourceLocation.image("shutter_lephon_l"), ResourceLocation.image("shutter_lephon_r"), ShutterAudio.ALT),
    UNDYING(ResourceLocation.image("shutter_undyingmacula_l"), ResourceLocation.image("shutter_undyingmacula_r"), ShutterAudio.ALT),
    CATCRY(ResourceLocation.image("shutter_catcry_l"), ResourceLocation.image("shutter_catcry_r"), ShutterAudio.ALT),
    DEINOS(ResourceLocation.image("shutter_deinosphainein_l"), ResourceLocation.image("shutter_deinosphainein_r"), ShutterAudio.ALT);

    private final ResourceLocation left;
    private final ResourceLocation right;
    private final ResourceLocation open;
    private final ResourceLocation close;

    Shutter(ResourceLocation left, ResourceLocation right, ShutterAudio audio) {
        this.left = left;
        this.right = right;
        this.open = audio.getOpen();
        this.close = audio.getClose();
    }

    public ResourceLocation getLeft() {
        return left;
    }

    public ResourceLocation getRight() {
        return right;
    }

    public ResourceLocation getOpen() {
        return open;
    }

    public ResourceLocation getClose() {
        return close;
    }
}
