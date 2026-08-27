package io.github.phiseecodyhsp.arcstory.ui.screen.viewmodel;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.model.story.Paragraph;
import io.github.phiseecodyhsp.arcstory.model.story.ParagraphType;
import io.github.phiseecodyhsp.arcstory.model.story.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoryViewModelTest {

    private static final ResourceLocation TEXT_LOC = ResourceLocation.text("test_text");
    private static final ResourceLocation CG1_LOC = ResourceLocation.image("cg_1");
    private static final ResourceLocation CG2_LOC = ResourceLocation.image("cg_2");

    private StoryViewModel viewModel;

    private Story makeStory(List<ResourceLocation> partnerLocations, List<Paragraph> paragraphs) {
        Story s = new Story();
        s.setPartnerLocations(partnerLocations);
        s.setParagraphs(paragraphs);
        return s;
    }

    @BeforeEach
    void setUp() {
        this.viewModel = null;
    }

    // 测试正常流程

    @Test
    void playNext_mixedParagraphs_progressesCorrectly() {
        List<ResourceLocation> partnerLocations = new ArrayList<>();
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.TEXT, TEXT_LOC));
        paragraphs.add(new Paragraph(ParagraphType.CG, CG1_LOC));
        paragraphs.add(new Paragraph(ParagraphType.CG, CG2_LOC));

        this.viewModel = new StoryViewModel(makeStory(partnerLocations, paragraphs), null);

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.TEXT, this.viewModel.getCurrentStatus());
        assertEquals(TEXT_LOC, this.viewModel.getCurrentText());
        assertNull(this.viewModel.getTopCg());
        assertNull(this.viewModel.getBottomCg());

        this.viewModel.markWaiting();
        assertEquals(StoryViewModel.Status.WAITING, this.viewModel.getCurrentStatus());

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.CG, this.viewModel.getCurrentStatus());
        assertEquals(CG1_LOC, this.viewModel.getTopCg());
        assertNull(this.viewModel.getBottomCg());

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.CG, this.viewModel.getCurrentStatus());

        this.viewModel.markWaiting();
        assertEquals(StoryViewModel.Status.WAITING, this.viewModel.getCurrentStatus());

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.CG, this.viewModel.getCurrentStatus());
        assertEquals(CG2_LOC, this.viewModel.getTopCg());
        assertEquals(CG1_LOC, this.viewModel.getBottomCg());

        this.viewModel.markWaiting();
        this.viewModel.proceed();

        assertEquals(StoryViewModel.Status.FINISHED, this.viewModel.getCurrentStatus());
    }

    // 测试文本未播放完毕时的步进尝试

    @Test
    void playNext_mixedParagraphs_proceedTextWhenNotWaiting() {
        List<ResourceLocation> partnerLocations = new ArrayList<>();
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.TEXT, TEXT_LOC));
        paragraphs.add(new Paragraph(ParagraphType.TEXT, TEXT_LOC));

        this.viewModel = new StoryViewModel(makeStory(partnerLocations, paragraphs), null);

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.TEXT, this.viewModel.getCurrentStatus());

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.WAITING, this.viewModel.getCurrentStatus());

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.TEXT, this.viewModel.getCurrentStatus());

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.WAITING, this.viewModel.getCurrentStatus());

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.FINISHED, this.viewModel.getCurrentStatus());
    }

    // 测试动画未播放完毕时的步进尝试

    @Test
    void playNext_mixedParagraphs_proceedCgWhenNotWaiting() {
        List<ResourceLocation> partnerLocations = new ArrayList<>();
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.CG, CG1_LOC));
        paragraphs.add(new Paragraph(ParagraphType.CG, CG2_LOC));

        this.viewModel = new StoryViewModel(makeStory(partnerLocations, paragraphs), null);

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.CG, this.viewModel.getCurrentStatus());

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.CG, this.viewModel.getCurrentStatus());

        this.viewModel.markWaiting();
        assertEquals(StoryViewModel.Status.WAITING, this.viewModel.getCurrentStatus());

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.CG, this.viewModel.getCurrentStatus());

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.CG, this.viewModel.getCurrentStatus());

        this.viewModel.markWaiting();
        assertEquals(StoryViewModel.Status.WAITING, this.viewModel.getCurrentStatus());

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.FINISHED, this.viewModel.getCurrentStatus());
    }

    // 测试边界情况

    @Test
    void playNext_singleParagraph_playsThenFinishes() {
        List<ResourceLocation> partnerLocations = new ArrayList<>();
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.TEXT, TEXT_LOC));

        this.viewModel = new StoryViewModel(makeStory(partnerLocations, paragraphs), null);

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.TEXT, this.viewModel.getCurrentStatus());
        assertEquals(TEXT_LOC, viewModel.getCurrentText());

        this.viewModel.markWaiting();
        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.FINISHED, this.viewModel.getCurrentStatus());
    }

    @Test
    void playNext_emptyList_finishesImmediately() {
        List<ResourceLocation> partnerLocations = new ArrayList<>();
        List<Paragraph> paragraphs = new ArrayList<>();

        this.viewModel = new StoryViewModel(makeStory(partnerLocations, paragraphs), null);

        this.viewModel.markWaiting();
        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.FINISHED, this.viewModel.getCurrentStatus());
    }

    @Test
    void playNext_allText_updatesCurrentTextEachTime() {
        ResourceLocation text2 = ResourceLocation.text("text_2");
        ResourceLocation text3 = ResourceLocation.text("text_3");
        List<ResourceLocation> partnerLocations = new ArrayList<>();
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.TEXT, TEXT_LOC));
        paragraphs.add(new Paragraph(ParagraphType.TEXT, text2));
        paragraphs.add(new Paragraph(ParagraphType.TEXT, text3));

        this.viewModel = new StoryViewModel(makeStory(partnerLocations, paragraphs), null);

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.TEXT, this.viewModel.getCurrentStatus());
        assertEquals(TEXT_LOC, this.viewModel.getCurrentText());

        this.viewModel.markWaiting();
        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.TEXT, this.viewModel.getCurrentStatus());
        assertEquals(text2, this.viewModel.getCurrentText());

        this.viewModel.markWaiting();
        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.TEXT, this.viewModel.getCurrentStatus());
        assertEquals(text3, this.viewModel.getCurrentText());

        this.viewModel.markWaiting();
        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.FINISHED, this.viewModel.getCurrentStatus());
    }

    @Test
    void playNext_allCg_lastCgTracksPrevious() {
        List<ResourceLocation> partnerLocations = new ArrayList<>();
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.CG, CG1_LOC));
        paragraphs.add(new Paragraph(ParagraphType.CG, CG2_LOC));
        paragraphs.add(new Paragraph(ParagraphType.CG,
                ResourceLocation.image("cg_3")));

        this.viewModel = new StoryViewModel(makeStory(partnerLocations, paragraphs), null);

        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.CG, this.viewModel.getCurrentStatus());
        assertEquals(CG1_LOC, this.viewModel.getTopCg());
        assertNull(this.viewModel.getBottomCg());

        this.viewModel.markWaiting();
        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.CG, this.viewModel.getCurrentStatus());
        assertEquals(CG2_LOC, this.viewModel.getTopCg());
        assertEquals(CG1_LOC, this.viewModel.getBottomCg());

        this.viewModel.markWaiting();
        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.CG, this.viewModel.getCurrentStatus());
        assertEquals(ResourceLocation.image("cg_3"), this.viewModel.getTopCg());
        assertEquals(CG2_LOC, this.viewModel.getBottomCg());
    }

    @Test
    void playNext_afterFinished_stillReturnsFinished() {
        List<ResourceLocation> partnerLocations = new ArrayList<>();
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.TEXT, TEXT_LOC));

        this.viewModel = new StoryViewModel(makeStory(partnerLocations, paragraphs), null);

        this.viewModel.proceed();
        this.viewModel.markWaiting();
        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.FINISHED, viewModel.getCurrentStatus());

        this.viewModel.markWaiting();
        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.FINISHED, viewModel.getCurrentStatus());

        this.viewModel.markWaiting();
        this.viewModel.proceed();
        assertEquals(StoryViewModel.Status.FINISHED, viewModel.getCurrentStatus());
    }

    // 属性初始状态

    @Test
    void properties_beforePlay_areNull() {
        List<ResourceLocation> partnerLocations = new ArrayList<>();
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.TEXT, TEXT_LOC));

        this.viewModel = new StoryViewModel(makeStory(partnerLocations, paragraphs), null);

        assertNull(this.viewModel.getTopCg());
        assertNull(this.viewModel.getCurrentText());
        assertNull(this.viewModel.getBottomCg());
    }

    // null 边界

    @Test
    void constructorNullStory_playNextThrowsNpe() {
        this.viewModel = new StoryViewModel(null, null);

        assertThrows(NullPointerException.class, () -> this.viewModel.proceed());
    }

    @Test
    void constructorNullParagraphs_playNextThrowsNpe() {
        Story s = new Story();
        s.setParagraphs(null);

        this.viewModel = new StoryViewModel(s, null);

        assertThrows(NullPointerException.class, () -> this.viewModel.proceed());
    }
}
