package effects.awesome.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.zip.Deflater;

public class ScratchWidget extends Image {
    private Pixmap pixmap, eraser;
    private Color scratchColor;
    private int eraserSize;
    private boolean pixmapChanged;

    public ScratchWidget(Color scratchColor) {
        super(new Texture("bg_spin.png"));
        this.scratchColor = scratchColor;
        pixmapChanged = false;
        this.setTouchable(Touchable.enabled);
        addListener(new ScratchEraserListener());
    }


    @Override
    public void layout() {
        super.layout();
        int width = (int) getWidth();
        int height = (int) getHeight();
        this.eraserSize = width / 10;
        if (pixmap == null) {
            pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
            pixmap.setColor(scratchColor);
            pixmap.setFilter(Pixmap.Filter.BiLinear);
            pixmap.fill();
            eraser = new Pixmap(eraserSize, eraserSize, Pixmap.Format.RGBA8888);
            eraser.setFilter(Pixmap.Filter.BiLinear);
            eraser.setColor(Color.CLEAR);
            eraser.fillCircle(eraserSize / 2, eraserSize / 2, eraserSize);
            Texture texture=new Texture(pixmap);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(texture);
            setDrawable(textureRegionDrawable);
            pixmapChanged = true;
        } else {
            if (pixmap.getWidth() != width || pixmap.getHeight() != height) {
                pixmap.dispose();
                pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
                pixmap.setFilter(Pixmap.Filter.BiLinear);
                pixmap.setColor(scratchColor);
                pixmap.fill();
                eraser.dispose();
                eraser = new Pixmap(eraserSize, eraserSize, Pixmap.Format.RGBA8888);
                eraser.setFilter(Pixmap.Filter.BiLinear);
                eraser.setColor(Color.CLEAR);
                eraser.fillCircle(eraserSize / 2, eraserSize / 2, eraserSize);
                Texture texture=new Texture(pixmap);
                texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(texture);
                setDrawable(textureRegionDrawable);
                pixmapChanged = true;
            }

        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (pixmapChanged) {
            Texture texture=new Texture(pixmap);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            TextureRegion region=new TextureRegion(texture);
            region.flip(false,true);
            TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(region);
            this.setDrawable(textureRegionDrawable);
            pixmapChanged = false;

        }
    }


    private void erase(float x, float y) {
        if (pixmap == null || eraser == null) return;
        if (x<0 || x>getWidth() || y<0 || y>getHeight())return;
        int pointX = (int) x;
        int pointY = (int) y;
        Gdx.app.error("Eraser Point", "x=>" + pointX + ",y=>" + pointY);
        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(Color.CLEAR);
        pixmap.fillCircle(pointX,pointY,eraserSize);
//        pixmap.drawPixmap(eraser, pointX, pointY);
        pixmapChanged = true;
    }


    private class ScratchEraserListener extends InputListener {
        private Vector2 temp = new Vector2();
        private int downPointer = -1;
        private Vector2 direction=new Vector2();
        private Vector2 lastPoint=new Vector2();

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (downPointer == -1) {
                downPointer = pointer;
                lastPoint.set(x,y);
                erase(x, y);
                return true;
            }
            return false;
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if (downPointer == pointer) {
                temp.set(x,y);
                direction.set(temp).sub(lastPoint).nor();
                float distance=temp.dst(lastPoint);
                while (distance>eraserSize/2f){
                    erase(lastPoint.x,lastPoint.y);
                    lastPoint.add(direction.scl(eraserSize/2f));
                    direction.nor();
                    distance=temp.dst(lastPoint);
                }
            }
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            downPointer = -1;
        }
    }
}
