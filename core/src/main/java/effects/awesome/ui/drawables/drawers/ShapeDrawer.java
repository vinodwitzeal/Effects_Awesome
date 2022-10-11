package effects.awesome.ui.drawables.drawers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class ShapeDrawer {
    public ShapeRenderer renderer;
    public ShapeRenderer.ShapeType shapeType;
    public ShapeDrawer(ShapeRenderer renderer,boolean filled){
        this.renderer=renderer;
        this.shapeType=filled? ShapeRenderer.ShapeType.Filled: ShapeRenderer.ShapeType.Line;
    }
    public abstract void draw(Batch batch, Color color, float x, float y, float width, float height);
}
