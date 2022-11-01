package effects.awesome.sweep;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SweepLine extends Group {
    private Image line1,line2;
    public SweepLine(TextureRegion region){
        line1=new Image(region);
        line2=new Image(region);
        addActor(line1);
        addActor(line2);
    }
}
