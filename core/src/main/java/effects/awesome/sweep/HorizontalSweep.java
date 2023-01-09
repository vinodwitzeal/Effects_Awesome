package effects.awesome.sweep;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HorizontalSweep extends Sweep{
    public HorizontalSweep(TextureRegion region,float height, Vector2 from, Vector2 to, Vector2 origin, Color color, int counts) {
        super(region, from, to, origin, color, counts);
        float width=from.dst(to);
        setSize(width,height);
        setOrigin(from.dst(origin),height/2f);
        float minLineHeight=height/(2f*lines.size());
        float maxLineHeight=height/(lines.size());
        Table lineTable=new Table();
        for (Image line:lines){
            float lineHeight= MathUtils.random(minLineHeight,maxLineHeight);
            lineTable.add(line).width(width).height(lineHeight).expandY();
            lineTable.row();
        }
        lineTable.setSize(width,height);
        lineTable.setTransform(true);
        addActor(lineTable);
        setPosition(from.x,from.y-height/2f);

        Image image=new Image(region);
        image.setSize(width,height);
        Color fadeColor=new Color(color);
        fadeColor.a*=0.5f;
        image.setColor(fadeColor);
        addActor(image);
    }

    @Override
    public void show(Stage stage) {

        setScaleX(0);
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
