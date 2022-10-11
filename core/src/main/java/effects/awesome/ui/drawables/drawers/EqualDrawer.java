package effects.awesome.ui.drawables.drawers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class EqualDrawer extends ShapeDrawer {

    public EqualDrawer(ShapeRenderer renderer) {
        super(renderer, true);
    }

    public EqualDrawer(ShapeRenderer renderer, boolean filled) {
        super(renderer, filled);
    }

    @Override
    public void draw(Batch batch, Color color, float x, float y, float width, float height) {
        batch.flush();
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.begin(shapeType);
        renderer.setColor(color);
        if (width >= height) {
            float radius = height / 2f;
            if (shapeType == ShapeRenderer.ShapeType.Filled) {
//                arc(renderer.getRenderer(),x+radius,y+radius,renderer.getColor(),radius,90,180);
//                arc(renderer.getRenderer(),x+width-radius,y+radius,renderer.getColor(),radius,270,180);
//                renderer.line(x+radius,y+height,x+width-radius,y+height);
//                renderer.line(x+radius,y,x+width-radius,y);
                renderer.arc(x + radius, y + radius, radius, 90, 180);
                renderer.arc(x + width - radius, y + radius, radius, 270, 180);
                renderer.rect(x + radius, y, width - height, height);
            } else {
                arc(renderer.getRenderer(),x+radius,y+radius,renderer.getColor(),radius,90,180,64);
                arc(renderer.getRenderer(),x+width-radius,y+radius,renderer.getColor(),radius,270,180,64);
                renderer.line(x+radius,y+height,x+width-radius,y+height);
                renderer.line(x+radius,y,x+width-radius,y);
            }
        } else {
            renderer.circle(x + width / 2f, y + height / 2f, height / 2f);
        }
        renderer.end();
    }

    public void arc(ImmediateModeRenderer renderer,float x, float y, Color color, float radius, float start, float degrees) {
        arc(renderer,x, y, color, radius, start, degrees, Math.max(1, (int) (12 * (float) Math.cbrt(radius) * (degrees / 360.0f))));
    }

    public void arc(ImmediateModeRenderer renderer,float x, float y, Color color, float radius, float start, float degrees, int segments) {
        float colorBits = color.toFloatBits();
        float theta = (2 * MathUtils.PI * (degrees / 360.0f)) / segments;
        float cos = MathUtils.cos(theta);
        float sin = MathUtils.sin(theta);
        float cx = radius * MathUtils.cos(start * MathUtils.degreesToRadians);
        float cy = radius * MathUtils.sin(start * MathUtils.degreesToRadians);
        renderer.color(colorBits);
        renderer.vertex(x + cx, y + cy, 0);
        renderer.color(colorBits);
        renderer.vertex(x + cx, y + cy, 0);
        for (int i = 0; i < segments; i++) {
            renderer.color(colorBits);
            renderer.vertex(x + cx, y + cy, 0);
            float temp = cx;
            cx = cos * cx - sin * cy;
            cy = sin * temp + cos * cy;
            renderer.color(colorBits);
            renderer.vertex(x + cx, y + cy, 0);
        }
        renderer.color(colorBits);
        renderer.vertex(x + cx, y + cy, 0);
        renderer.color(colorBits);
        renderer.vertex(x + cx, y + cy, 0);
    }
}
