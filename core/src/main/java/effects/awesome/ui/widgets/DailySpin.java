package effects.awesome.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import effects.awesome.ui.RadialSprite;

public class DailySpin extends WidgetGroup {
    private final int REST=0,SPINNING=1,STOPPING=2,STOPPED=3;
    private int state;
    private float targetAngle;
    private Texture texture;
    private float speed=360f;
    private TextureRegion iconRegion;
    public DailySpin(int sectors,float offsetAngle) {
        setTransform(true);
        iconRegion=new TextureRegion(new Texture("bg_circle.png"));
        texture = new Texture("bg_spin.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture);
        for (int i = 0; i < sectors; i++) {
            addActor(new SectorGroup(region, i, sectors,offsetAngle));
        }
        state=REST;
    }

    public void spin(){
//        state=SPINNING;
    }

    public void stopSpin(int targetIndex){
        targetAngle=360f-360f*targetIndex/getChildren().size;
        state=STOPPING;
    }

    protected void spinnerStopped(){

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (state==SPINNING){
            rotate(delta);
        }

        if (state==STOPPING){
            float rotation=getRotation();
            if (rotation<targetAngle){
                float difference=targetAngle-rotation;
                if (difference<=5.0f){
                    setRotation(targetAngle);
                    spinnerStopped();
                    state=STOPPED;
                }else{
                    rotate(delta);
                }

            }else {
               rotate(delta);
            }
        }
    }

    private  void rotate(float delta){
        float rotation=getRotation()+speed*delta;
        if (rotation>360f){
            rotation=rotation-360f;
        }
        setRotation(rotation);
    }

    @Override
    public void layout() {
        for (Actor actor : getChildren()) {
            actor.setSize(getWidth(), getHeight());
            actor.setOrigin(getWidth() / 2f, getHeight() / 2f);
            actor.setPosition(0, 0);
        }
    }

    public class SectorGroup extends WidgetGroup {
        private Sector sector;
        private SectorDetails details;

        public SectorGroup(TextureRegion region, int index, int sectors,float offsetAngle) {
            sector = new Sector(region);
            setFillParent(true);
            float value = (float) index / (float) sectors;
            float angle=360f/sectors;
            sector.setAngle(angle);
            sector.setRotation(180+angle/2f);
            sector.setFillParent(true);
            Color color = new Color(1, 1, 1, 1);
            sector.setColor(color.lerp(0.5f, 0.5f, 0.5f, 1, value));
            details=new SectorDetails((index+1)+"","");
            details.top();
            addActor(sector);
            addActor(details);
            setRotation(360f*index/sectors+offsetAngle);
        }

        @Override
        public void layout() {
            sector.setSize(getWidth(),getHeight());
            sector.setPosition(0,0);
            sector.setOrigin(getWidth()/2f,getHeight()/2f);
            details.padBottom(getWidth()/2f);
            details.setSize(getWidth(),getHeight());
            details.setPosition(0,0);
            details.setOrigin(details.getWidth()/2f,details.getHeight()/2f);
        }

    }

    public class Sector extends WidgetGroup {
        private RadialSprite sprite;

        public Sector(TextureRegion region) {
            setTransform(true);
            sprite = new RadialSprite(region);
        }

        public void setAngle(float angle) {
            sprite.setAngle(360f-angle);
        }

        public void setColor(Color color) {
            sprite.setColor(color);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            applyTransform(batch, computeTransform());
            sprite.draw(batch, getX(), getY(), getWidth(), getHeight());
            resetTransform(batch);
        }
    }

    public class SectorDetails extends Table{
        public SectorDetails(String prizeValue,String description){
//            UILabel.UILabelStyle titleStyle=new UILabel.UILabelStyle(FontType.Noto,16);
//            titleStyle.fontColor=Color.BLACK;
//            UILabel titleLabel=new UILabel(prizeValue,titleStyle);
//            add(titleLabel).row();
//            Image image=new Image(iconRegion);
//            float imageSize=titleStyle.font.getLineHeight();
//            add(image).width(imageSize).height(imageSize);
        }
    }
}
