package effects.awesome.ui.drawables.drawers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class RoundedBoxDrawer extends ShapeDrawer{
    private float bottomLeft,topLeft,topRight,bottomRight;

    public RoundedBoxDrawer(ShapeRenderer renderer,float radius){
        this(renderer,true,radius,radius,radius,radius);
    }

    public RoundedBoxDrawer(ShapeRenderer renderer,boolean filled,float radius){
        this(renderer,filled,radius,radius,radius,radius);
    }

    public RoundedBoxDrawer(ShapeRenderer renderer,float bottomLeft,float topLeft,float topRight,float bottomRight) {
        this(renderer, true,bottomLeft,topLeft,topRight,bottomRight);
    }

    public RoundedBoxDrawer(ShapeRenderer renderer, boolean filled,float bottomLeft,float topLeft,float topRight,float bottomRight) {
        super(renderer, filled);
        this.bottomLeft=bottomLeft;
        this.topLeft=topLeft;
        this.topRight=topRight;
        this.bottomRight=bottomRight;
    }


    @Override
    public void draw(Batch batch, Color color, float x, float y, float width, float height) {
        batch.flush();
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.begin(shapeType);
        renderer.setColor(color);

        renderer.end();
    }
}
