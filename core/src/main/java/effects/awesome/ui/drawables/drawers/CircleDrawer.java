package effects.awesome.ui.drawables.drawers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class CircleDrawer extends ShapeDrawer{
    public CircleDrawer(ShapeRenderer renderer) {
        this(renderer,true);
    }

    public CircleDrawer(ShapeRenderer renderer, boolean filled){
        super(renderer,filled);
    }

    @Override
    public void draw(Batch batch, Color color, float x, float y, float width, float height) {
        batch.flush();
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.begin(shapeType);
        renderer.setColor(color);
        renderer.circle(x+width/2f,y+height/2f, Math.min(width,height)/2f);
        renderer.end();
    }
}
