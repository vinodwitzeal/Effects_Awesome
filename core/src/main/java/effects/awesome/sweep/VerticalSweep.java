package effects.awesome.sweep;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class VerticalSweep extends Sweep{
    public VerticalSweep(TextureRegion region,float width, Vector2 from, Vector2 to, Vector2 origin, Color color, int counts) {
        super(region, from, to, origin, color, counts);
        float height=from.dst(to);
        setSize(width,height);
        setOrigin(width/2f,from.dst(origin));
        float minLineWidth=width/(2f*lines.size());
        float maxLineWidth=width/(lines.size());
        Table lineTable=new Table();
        for (Image line:lines){
            float lineWidth= MathUtils.random(minLineWidth,maxLineWidth);
            lineTable.add(line).width(lineWidth).height(height).expandX();
        }
        lineTable.setSize(width,height);
        lineTable.setTransform(true);
        addActor(lineTable);
        setPosition(from.x-width/2f,from.y);

        Image image=new Image(region);
        image.setSize(width,height);
        Color fadeColor=new Color(color);
        fadeColor.a=0.5f;
        image.setColor(fadeColor);
        addActor(image);
    }

    @Override
    public void show(Stage stage) {
        setScaleY(0);
        addAction(Actions.sequence(
                Actions.fadeOut(0),
                Actions.parallel(
                        Actions.fadeIn(0.5f),
                        Actions.scaleTo(1,1,0.5f)
                ),
                Actions.fadeOut(0.2f),
                Actions.removeActor()
        ));
        stage.addActor(this);
    }
}
