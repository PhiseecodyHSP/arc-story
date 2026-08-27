package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.deprecated.state.GameState;
import io.github.phiseecodyhsp.arcstory.deprecated.state.SaveManager;
import io.github.phiseecodyhsp.arcstory.res.AudioManager;
import io.github.phiseecodyhsp.arcstory.res.ResourceLoader;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.ui.screen.StoryScreen;
import io.github.phiseecodyhsp.arcstory.ui.screen.viewmodel.StoryScreenViewModel;
import io.github.phiseecodyhsp.arcstory.ui.screen.viewmodel.StoryRequirementViewModel;
import io.github.phiseecodyhsp.arcstory.ui.screen.viewmodel.StoryViewModel;
import io.github.phiseecodyhsp.arcstory.viewmodel.node.AvatarNodeViewModel;
import io.github.phiseecodyhsp.arcstory.viewmodel.StoryBranchViewModel;
import io.github.phiseecodyhsp.arcstory.ui.base.AppWindow;
import io.github.phiseecodyhsp.arcstory.ui.base.ScreenManager;
import io.github.phiseecodyhsp.arcstory.viewmodel.node.ButtonNodeViewModel;
import io.github.phiseecodyhsp.arcstory.viewmodel.node.StoryEndpointNodeViewModel;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class ArcStoryLauncher extends Application {

    private static ArcStoryLauncher instance;

    private AppWindow appWindow;
    private AudioManager audioManager;
    private GameState gameState;

    @Override
    public void start(Stage stage) {
        instance = this;
        loadGameState();

        this.audioManager = new AudioManager();
        this.appWindow = new AppWindow(stage);
        this.registerScreens(this.appWindow.getScreenManager());

//        this.audioManager.playBgm(ResourceLocation.audio("story_loop_bgm"));
    }

    @Override
    public void stop() {
        if (this.audioManager != null) {
            this.audioManager.stopBgm();
        }
        saveGameState();
    }

    private void loadGameState() {
        try {
            this.gameState = SaveManager.load();
        } catch (IOException e) {
            this.gameState = new GameState();
        }
    }

    private void saveGameState() {
        if (this.gameState != null) {
            try {
                SaveManager.save(this.gameState);
            } catch (IOException ignored) {
            }
        }
    }

    private void registerScreens(ScreenManager screenManager) {
        // For testing
        StoryScreenViewModel storyScreenViewModel = new StoryScreenViewModel(ResourceLocation.image("cg_2_1"));
        StoryScreen storyScreen = new StoryScreen(storyScreenViewModel);

        Runnable onRequirementFinishedCallback = () -> storyScreenViewModel.setRequirementView(null);
        Consumer<ResourceLocation> onRequirementShownCallback = loc ->
                storyScreenViewModel.setRequirementView(
                        new StoryRequirementViewModel(ResourceLoader.loadStoryRequirement(loc), onRequirementFinishedCallback)
                );

        Runnable onStoryFinishedCallback = () -> storyScreenViewModel.setStoryView(null);
        Consumer<ResourceLocation> onStoryShownCallback = loc ->
                storyScreenViewModel.setStoryView(
                        new StoryViewModel(ResourceLoader.loadStory(loc), onStoryFinishedCallback)
                );

        StoryBranchViewModel branch1 = new StoryBranchViewModel();
        ButtonNodeViewModel buttonNode = new ButtonNodeViewModel("Button", ResourceLocation.image("tutorial_illustration"));
        buttonNode.setOnMouseClicked(_ -> System.out.println("Clicked"));
        branch1.getStoryNodes().addAll(new AvatarNodeViewModel(ResourceLoader.loadPartner(ResourceLocation.partner("hikari"))),
                new StoryEndpointNodeViewModel("Test", ResourceLocation.image("tutorial_illustration"), ResourceLocation.story("test"), onStoryShownCallback),
                buttonNode);



        StoryBranchViewModel branch2 = new StoryBranchViewModel();
        branch2.getStoryNodes().addAll(new StoryEndpointNodeViewModel("A1", ResourceLocation.image("tutorial_illustration"), ResourceLocation.story("empty"), onStoryShownCallback),
                new StoryEndpointNodeViewModel("A2", ResourceLocation.image("tutorial_illustration"), ResourceLocation.storyRequirement("test"), ResourceLocation.story("test"), onRequirementShownCallback, onStoryShownCallback),
                new StoryEndpointNodeViewModel("A3", ResourceLocation.image("tutorial_illustration"), ResourceLocation.story("test"), onStoryShownCallback));
        storyScreenViewModel.getStoryBranches().addAll(branch1, branch2);
        screenManager.register(storyScreen);
        screenManager.setInitialScreen(storyScreen);
    }

    public static ArcStoryLauncher getInstance() {
        return instance;
    }

    public AppWindow getAppWindow() {
        return this.appWindow;
    }

    public AudioManager getAudioManager() {
        return this.audioManager;
    }

    public GameState getGameState() {
        return this.gameState;
    }
}
