package effects.awesome.ui.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import effects.awesome.mask.MaskShader;

public class MaskedImage extends Image implements MaskShader.ActorDrawer {
    private TextureRegion mask;
    private MaskShader maskShader;
    public MaskedImage(Texture mask,Texture texture){
        super(texture);
        this.mask=new TextureRegion(mask);
        this.maskShader=MaskShader.instance();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (maskShader.isCompiled()) {
            validate();
            maskShader.mask(batch, mask, parentAlpha, this);
            maskShader.draw(batch,parentAlpha,this);
        }else {
            super.draw(batch,parentAlpha);
        }
    }

    @Override
    public Actor getActor() {
        return this;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch,1.0f);
    }
}
