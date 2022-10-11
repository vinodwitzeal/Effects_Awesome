package effects.awesome.ui.widgets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class GiftAnimation extends Widget {
    private Animation<TextureRegion> animation;
    private float currentTime,totalDuration;
    public GiftAnimation(){
        TextureAtlas atlas=new TextureAtlas("gift.atlas");
        animation=new Animation<TextureRegion>(0.04f,atlas.findRegions("gifthome"), Animation.PlayMode.LOOP);
        totalDuration=animation.getAnimationDuration();
        currentTime=0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        currentTime+=delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(animation.getKeyFrame(currentTime),getX(),getY(),getWidth(),getHeight());
        if (currentTime>=totalDuration){
            currentTime=0;
        }
    }
}
