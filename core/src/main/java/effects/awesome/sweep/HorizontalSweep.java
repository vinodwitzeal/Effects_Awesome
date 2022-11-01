package effects.awesome.sweep;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class HorizontalSweep extends Sweep{
    private ShapeDrawer shapeDrawer;
    public HorizontalSweep(TextureRegion region, Color color) {
        super(region, color);
    }

    public HorizontalSweep(TextureRegion region, Color color, int counts) {
        super(region, color, counts);
    }

//    @Override
//    protected void layoutSweep() {
//        float width=getWidth();
//        float height=getHeight();
//
//        float lineX=0;
//        float lineY=0;
//        for (Image line:lines){
//            float lineHeight=height/3f;
//            line.setSize(width,lineHeight);
//            line.setPosition(lineX,lineY);
//            lineY+=lineHeight;
//        }
//    }


    @Override
    protected void layoutSweep() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (shapeDrawer==null){
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.WHITE);
            pixmap.drawPixel(0, 0);
            Texture texture = new Texture(pixmap); //remember to dispose of later
            pixmap.dispose();
            TextureRegion region = new TextureRegion(texture, 0, 0, 1, 1);
            shapeDrawer=new ShapeDrawer(batch,region);
        }

        shapeDrawer.filledCircle(getX(),getY(),getHeight()/2f);
        shapeDrawer.circle(getX(),getY(),getHeight(),10);
    }
}
