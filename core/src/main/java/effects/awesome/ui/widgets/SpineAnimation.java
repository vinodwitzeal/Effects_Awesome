package effects.awesome.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

public class SpineAnimation extends Widget {
    private SkeletonRenderer renderer;
    private Skeleton skeleton;
    private AnimationState state;

    private TextureAtlas atlas;
    public SpineAnimation(){
        renderer=new SkeletonRenderer();
        renderer.setPremultipliedAlpha(true);

        atlas=new TextureAtlas("home_gift.atlas");
        SkeletonJson json=new SkeletonJson(atlas);
        SkeletonData skeletonData= json.readSkeletonData(Gdx.files.internal("home_gift.json"));
        skeleton=new Skeleton(skeletonData);

        AnimationStateData stateData=new AnimationStateData(skeletonData);
        state=new AnimationState(stateData);
        state.addAnimation(0,"animation_open",true,0.0f);
        state.setAnimation(0,"animation_open",true);
    }

    @Override
    public void layout() {
        super.layout();
        skeleton.setPosition(getWidth()/2f,getHeight()/2f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        state.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        state.apply(skeleton);
        skeleton.updateWorldTransform();
        renderer.draw(batch,skeleton);
    }
}
