package effects.awesome.ui.drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

import effects.awesome.ui.drawables.drawers.ShapeDrawer;

public class ShapeDrawable extends BaseDrawable {
    private ShapeDrawer drawer;
    private Color color;
    public ShapeDrawable(ShapeDrawer drawer, Color color){
        this.drawer=drawer;
        this.color=color;
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        if (drawer==null)return;
        SpriteBatch spriteBatch;
        drawer.draw(batch,color,x,y,width,height);
    }
}
