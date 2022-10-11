package effects.awesome.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MaskedWidget extends Table {
    private TextureRegion mask;
    public MaskedWidget(Actor actor, TextureRegion mask){
        this.add(actor).expand().fill();
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
        batch.setBlendFunction(Gdx.gl.GL_SRC_ALPHA,Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void drawAlphaMask(Batch batch,float parentAlpha){
        batch.setColor(1,1, 1, 1);
        Gdx.gl.glColorMask(false, false, false, true);
        batch.setBlendFunction(GL20.GL_ONE, Gdx.gl.GL_ZERO);
        batch.draw(mask,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
        batch.setBlendFunction(GL20.GL_ZERO,GL20.GL_SRC_ALPHA);
        super.draw(batch,parentAlpha);
        batch.flush();
    }
}
