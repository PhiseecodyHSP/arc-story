package io.github.phiseecodyhsp.arcstory.ui.screen.viewmodel;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.ui.screen.StoryScreen;
import io.github.phiseecodyhsp.arcstory.viewmodel.StoryBranchViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @see StoryScreen
 *
 * @author RikkaKawaii0612
 */
public class StoryScreenViewModel {

    private final ObservableList<StoryBranchViewModel> storyBranches = FXCollections.observableArrayList();

    private final ObjectProperty<ResourceLocation> background;

    private final ObjectProperty<StoryViewModel> storyView = new SimpleObjectProperty<>();

    private final ObjectProperty<StoryRequirementViewModel> conditionView = new SimpleObjectProperty<>();

    public StoryScreenViewModel(ResourceLocation background) {
        this.background = new SimpleObjectProperty<>(background);
    }

    public ObservableList<StoryBranchViewModel> getStoryBranches() {
        return this.storyBranches;
    }

    public ResourceLocation getBackground() {
        return this.background.get();
    }

    public ObjectProperty<ResourceLocation> backgroundProperty() {
        return this.background;
    }

    public StoryViewModel getStoryView() {
        return this.storyView.get();
    }

    public void setStoryView(StoryViewModel storyView) {
        this.storyView.setValue(storyView);
    }

    public ObjectProperty<StoryViewModel> storyViewProperty() {
        return this.storyView;
    }

    public void setConditionView(StoryRequirementViewModel conditionView) {
        this.conditionView.setValue(conditionView);
    }

    public ObjectProperty<StoryRequirementViewModel> conditionViewProperty() {
        return this.conditionView;
    }
}