package io.github.phiseecodyhsp.arcstory.viewmodel.node;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class StoryEndpointNodeViewModel extends ButtonNodeViewModel {

    private final ObjectProperty<ResourceLocation> storyLocation;

    private final ObjectProperty<ResourceLocation> requirementLocation;

    public StoryEndpointNodeViewModel(@NotNull String title,
                                      @NotNull ResourceLocation illustrationLocation,
                                      @NotNull ResourceLocation storyLocation,
                                      @NotNull Consumer<ResourceLocation> onStoryShownCallback) {
        this(title, illustrationLocation, null, storyLocation, null, onStoryShownCallback);
    }

    public StoryEndpointNodeViewModel(@NotNull String title,
                                      @NotNull ResourceLocation illustrationLocation,
                                      @Nullable ResourceLocation requirementLocation,
                                      @NotNull ResourceLocation storyLocation,
                                      @Nullable Consumer<ResourceLocation> onRequirementShownCallback,
                                      @NotNull Consumer<ResourceLocation> onStoryShownCallback) {
        super(title, illustrationLocation);

        this.requirementLocation = new SimpleObjectProperty<>(requirementLocation);
        this.storyLocation = new SimpleObjectProperty<>(storyLocation);

        //若故事解锁条件存在, 则在被 enable 时将鼠标点击事件设为展示故事解锁条件; 否则直接 unlock
        if (requirementLocation != null && onRequirementShownCallback != null) {
            this.enabledProperty().addListener((_, _, b) -> {
                if (b) {
                    this.setOnMouseClicked(_ -> onRequirementShownCallback.accept(this.requirementLocation.get()));
                }
            });
        } else {
            this.enabledProperty().addListener((_, _, b) -> {
                if (b) {
                    this.unlock();
                }
            });
        }

        //被 unlock 时将鼠标点击事件设为播放故事
        this.lockedProperty().addListener((_, _, b) -> {
            if (!b) {
                this.setOnMouseClicked(_ -> onStoryShownCallback.accept(this.storyLocation.get()));
            }
        });
    }

    public ResourceLocation getStoryLocation() {
        return this.storyLocation.get();
    }

    public ObjectProperty<ResourceLocation> storyLocationProperty() {
        return this.storyLocation;
    }
}
