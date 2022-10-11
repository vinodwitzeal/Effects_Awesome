package effects.awesome.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.graphics.glutils.HdpiMode;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class CarousalPage extends Table implements Cloneable{
    private final float padding;
    private final float bigScale = 1.0f;
    private final float smallScale = 0.2f;
    private final float diffScale=bigScale-smallScale;
    private boolean flippedBack;

    public CarousalPage(float padding) {
        setTransform(true);
        this.padding=padding;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float defaultWidth = getWidth();
        float defaultHeight = getHeight();
        float pagePosition = (getX() - padding) / defaultWidth;
        float scale = bigScale;
        float pageWidth = defaultWidth * scale;
        float pageX = getX() + (defaultWidth - pageWidth) / 2;

        if (pagePosition < 0) {
            scale = scale + pagePosition * diffScale;
            pageWidth = defaultWidth * scale;
            pageX = getX() + (defaultWidth - pageWidth);
            if (pagePosition < -0.1f && isFlippedBack()) {
                flipToFront();
            }
        } else if (pagePosition > 0) {
            scale = scale - pagePosition * diffScale;
            pageWidth = defaultWidth * scale;
            pageX=getX();
            if (pagePosition > 0.1f && isFlippedBack()) {
                flipToFront();
            }
        }

        float pageHeight = defaultHeight;
        float pageY = getY() + (defaultHeight - pageHeight) / 2;

        setX(pageX);
        setY(pageY);
        setScaleX(scale);
        super.draw(batch,parentAlpha);
    }

    public void flipToFront(){

    }

    public void setFlippedBack(boolean flippedBack){
        this.flippedBack=flippedBack;
    }

    public boolean isFlippedBack(){
        return this.flippedBack;
    }

}
