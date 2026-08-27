package io.github.phiseecodyhsp.arcstory.ui.screen.view;

import io.github.phiseecodyhsp.arcstory.ui.screen.StoryScreen;
import io.github.phiseecodyhsp.arcstory.ui.screen.viewmodel.StoryScreenViewModel;
import io.github.phiseecodyhsp.arcstory.view.StoryBranch;
import io.github.phiseecodyhsp.arcstory.view.StoryNodeUiConstants;
import io.github.phiseecodyhsp.arcstory.viewmodel.StoryBranchViewModel;
import io.github.phiseecodyhsp.arcstory.util.PropertyUtil;
import io.github.phiseecodyhsp.arcstory.ui.util.ScreenMetrics;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * @see StoryScreen
 *
 * @author RikkaKawaii0612
 */
public class StoryScreenView extends StackPane {

    /**
     * 两个相邻故事分支的间距.
     */
    private static final double BRANCH_SPACING = StoryNodeUiConstants.DIAGONAL_LENGTH * 2.0D;

    private final StoryScreenViewModel viewModel;

    private final ObservableMap<StoryBranchViewModel, StoryBranch> storyBranches = FXCollections.observableHashMap();

    private final ObjectProperty<StoryRequirementView> requirementView = new SimpleObjectProperty<>();

    private final ObjectProperty<StoryView> storyView = new SimpleObjectProperty<>();

    public StoryScreenView(StoryScreenViewModel viewModel) {
        this.viewModel = viewModel;

        ImageView background = new ImageView();
        background.imageProperty().bind(PropertyUtil.createImage(this.viewModel.backgroundProperty()));
        background.setPreserveRatio(true);
        background.setFitWidth(ScreenMetrics.SCREEN_WIDTH);

        this.getChildren().add(background);

        // 监听 ViewModel, 自动增删子 View
        this.viewModel.getStoryBranches().addListener(this::onStoryBranchesChanged);
        this.viewModel.getStoryBranches().forEach(this::addStoryBranch);

        this.requirementView.addListener((_, oldVar, newVar) -> {
            if (oldVar != null) {
                this.getChildren().remove(oldVar);
            }
            if (newVar != null) {
                this.getChildren().add(newVar);
                newVar.show();
            }
        });

        this.storyView.addListener((_, oldVar, newVar) -> {
            if (oldVar != null) {
                this.getChildren().remove(oldVar);
            }
            if (newVar != null) {
                this.getChildren().add(newVar);
                newVar.start();
            }
        });

        this.viewModel.requirementViewProperty().addListener((_, _, v) -> {
            StoryRequirementView requirementView = v == null ? null : new StoryRequirementView(v);
            this.requirementView.setValue(requirementView);
        });

        this.viewModel.storyViewProperty().addListener((_, _, v) -> {
            StoryView storyView = v == null ? null : new StoryView(v);
            this.storyView.setValue(storyView);
        });
    }

    private void onStoryBranchesChanged(ListChangeListener.Change<? extends StoryBranchViewModel> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for (StoryBranchViewModel viewModel : change.getAddedSubList()) {
                    this.addStoryBranch(viewModel);
                }
            }
            if (change.wasRemoved()) {
                for (StoryBranchViewModel viewModel : change.getRemoved()) {
                    this.removeStoryBranch(viewModel);
                }
            }
        }

        this.requestLayout();
    }

    private void addStoryBranch(StoryBranchViewModel viewModel) {
        StoryBranch storyBranch = new StoryBranch(viewModel);
        this.storyBranches.put(viewModel, storyBranch);
        this.getChildren().add(storyBranch);
    }

    private void removeStoryBranch(StoryBranchViewModel viewModel) {
        StoryBranch storyBranch = this.storyBranches.remove(viewModel);
        if (storyBranch != null) {
            this.getChildren().remove(storyBranch);
        }
    }

    @Override
    protected void layoutChildren() {
        ObservableList<StoryBranchViewModel> list = this.viewModel.getStoryBranches();
        int count = list.size();

        // 从上到下按顺序布局故事分支, 左对齐, 整体居中
        double width = 0.0D;
        for (int i = 0; i < count; i++) {
            StoryBranchViewModel viewModel = list.get(i);
            this.storyBranches.get(viewModel).setTranslateY(0.5D * BRANCH_SPACING * (1.0D - count + 2.0D * i));
            width = Math.max(width, StoryBranch.NODE_SPACING * (viewModel.getStoryNodes().size() - 1.0D));
        }

        for (int i = 0; i < count; i++) {
            StoryBranchViewModel viewModel = list.get(i);
            this.storyBranches.get(viewModel).setTranslateX(-0.5D * width);
        }

        super.layoutChildren();
    }
}