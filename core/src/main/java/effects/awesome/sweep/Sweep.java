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

public abstract class Sweep extends Group {
    protected Vector2 from,to,origin;
    protected List<Image> lines;
    public Sweep(TextureRegion region,Vector2 from,Vector2 to,Vector2 origin,Color color,int counts){
        this.from=from;
        this.to=to;
        this.origin=origin;
        lines=new ArrayList<Image>();
        NinePatchDrawable drawable=new NinePatchDrawable(new NinePatch(region,1,1,1,1));
        for (int i=0;i<counts;i++){
            Image line=new Image(region);
            line.setColor(color);
            lines.add(line);
        }
    }


    public abstract void show(Stage stage);

}
