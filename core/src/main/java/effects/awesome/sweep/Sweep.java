package effects.awesome.sweep;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import java.util.ArrayList;
import java.util.List;

public abstract class Sweep extends WidgetGroup {
    protected List<Image> lines;
    public Sweep(TextureRegion region,Color color,int counts){
        lines=new ArrayList<Image>();
        NinePatchDrawable drawable=new NinePatchDrawable(new NinePatch(region,1,1,1,1));
        for (int i=0;i<counts;i++){
            Image line=new Image(drawable);
            line.setColor(color);
            lines.add(line);
            addActor(line);
        }
    }

    public Sweep(TextureRegion region,Color color){
        this(region,color,3);
    }

    @Override
    public void layout() {
        super.layout();
        layoutSweep();
    }

    protected abstract void layoutSweep();

    public void show(Group parent){
        parent.addActor(this);
        for (Image line:lines){
            line.addAction(Actions.sequence(
                    Actions.fadeOut(0),
                    Actions.fadeIn(0.2f),
                    Actions.delay(0.5f,Actions.fadeOut(0.5f))
            ));
        }
        addAction(Actions.delay(1.2f,Actions.removeActor()));
    }
}
