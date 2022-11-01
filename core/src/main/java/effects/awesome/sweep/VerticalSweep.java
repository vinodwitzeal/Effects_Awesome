package effects.awesome.sweep;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class VerticalSweep extends Sweep{
    public VerticalSweep(TextureRegion region, Color color) {
        super(region, color);
    }

    public VerticalSweep(TextureRegion region, Color color, int counts) {
        super(region, color, counts);
    }

    @Override
    protected void layoutSweep() {
        float width=getWidth();
        float height=getHeight();
        float lineWidth=width/lines.size();

        float lineX=0;
        float lineY=0;
        for (Image line:lines){
            line.setSize(lineWidth,height);
            line.setPosition(lineX,lineY);
            lineX+=lineWidth;
        }
    }
}
