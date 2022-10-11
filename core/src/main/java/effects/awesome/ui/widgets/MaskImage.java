package effects.awesome.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MaskImage extends Image {
    private TextureRegion mask;
    public MaskImage(TextureRegion region,TextureRegion mask){
        super(region);
        this.mask=mask;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.flush();
        drawAlphaMask(batch,parentAlpha);
        Gdx.gl.glColorMask(true, true, true, true);
        batch.setBlendFunction(GL20.GL_DST_ALPHA,GL20.GL_ONE_MINUS_DST_ALPHA);
        super.draw(batch, parentAlpha);
        batch.flush();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void drawAlphaMask(Batch batch,float parentAlpha){
        batch.setColor(1,1, 1, 1);
        Gdx.gl.glColorMask(false, false, false, true);
        batch.setBlendFunction(GL20.GL_ONE, Gdx.gl.GL_SRC_ALPHA);
        batch.draw(mask,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
        batch.setBlendFunction(GL20.GL_ZERO,GL20.GL_SRC_ALPHA);
        super.draw(batch,parentAlpha);
        batch.flush();
    }
}
