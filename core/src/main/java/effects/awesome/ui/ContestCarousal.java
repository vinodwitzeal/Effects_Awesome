package effects.awesome.ui;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import java.util.ArrayList;
import java.util.List;

public class ContestCarousal extends WidgetGroup {
    public static final int DIRECTION_VERTICAL = 1, DIRECTION_HORIZONTAL = 2;
    private ContestCarousalStyle style;
    private float differenceScale;
    private List<Actor> pages;
    private final int direction;
    private boolean canScroll;
    private float scrollAmount;
    private float minPosition, center, maxPosition;
    private float startDistance, endDistance;

    public ContestCarousal(ContestCarousalStyle style, int direction) {
        this.style = style;
        this.differenceScale = style.maxScale - style.minScale;
        this.pages = new ArrayList<Actor>();
        this.direction = direction;
        this.canScroll = false;
//        addListener(new ScrollListener());
    }

    @Override
    public float getPrefHeight() {
        return style.pageHeight + 2 * style.gutterHeight;

    }

    @Override
    public float getPrefWidth() {
        return style.pageWidth + 2 * style.gutterWidth;
    }


    public void addPage(Actor page) {
        pages.add(page);
        page.setSize(style.pageWidth, style.pageHeight);
        page.setOrigin(style.pageWidth / 2f, style.pageHeight / 2f);
        addActor(page);
    }

    @Override
    public void layout() {
        super.layout();
        canScroll = pages.size() > 1;

        if (canScroll) {
            if (direction == DIRECTION_HORIZONTAL) {
                center = getWidth() / 2f;
            } else {
                center = getHeight() / 2f;
            }
        } else {
            if (pages.size() == 1) {
                Actor page = pages.get(0);
                page.setPosition((getWidth() - page.getWidth()) / 2f, (getHeight() - page.getHeight()) / 2f);
            }
        }
    }

    protected void drawPages(Batch batch, float parentAlpha) {
        if (canScroll) {

        }
    }

    private class ScrollListener extends InputListener {
        private boolean touchDown;
        private boolean panning;
        private float tempTouch;
        private float tapDistance;
        public ScrollListener(float tapDistance){
            touchDown=false;
            panning=false;
            this.tapDistance=tapDistance;
        }
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (pointer==0 && !touchDown){
                if (direction==DIRECTION_HORIZONTAL){
                    tempTouch=x;
                }else {
                    tempTouch=y;
                }
                touchDown=true;
                return true;
            }

            return false;
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if (touchDown){
                float delta=0;
                float distance;
                if (direction==DIRECTION_HORIZONTAL){
                    delta=x-tempTouch;
                    tempTouch=x;
                }else{
                    delta=y-tempTouch;
                    tempTouch=y;
                }
                distance=Math.abs(delta);
                if (distance>=tapDistance){
                    scrollAmount+=delta;
                    panning=true;
                }
            }
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            super.touchUp(event, x, y, pointer, button);
        }
    }

    public static class ContestCarousalStyle {
        public float pageWidth, pageHeight;
        public float gutterWidth, gutterHeight;
        public float minScale = 0.4f;
        public float maxScale = 1.0f;
    }
}
