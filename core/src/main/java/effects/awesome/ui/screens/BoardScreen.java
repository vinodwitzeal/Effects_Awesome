package effects.awesome.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EqualTransformGroup;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.TransformGroup;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

import effects.awesome.ScreenController;
import effects.awesome.explosion.CandyBreak;
import effects.awesome.lightning.MovingLightningBolt;
import effects.awesome.lightning.SimpleLightningBolt;
import effects.awesome.sweep.HorizontalSweep;
import effects.awesome.sweep.VerticalSweep;
import effects.awesome.water.Water;

public class BoardScreen extends UIScreen{
    private Water water;
    public BoardScreen(ScreenController controller) {
        super(controller);
    }

    @Override
    protected void buildStage() {
        int screenWidth= Gdx.graphics.getWidth();
        int screenHeight=Gdx.graphics.getHeight();


        width=screenWidth;
        height=screenHeight;

        FitViewport fitViewport=new FitViewport(width,height);
        stage=new Stage(fitViewport,new PolygonSpriteBatch());
    }


    private TextureRegion findRegion(String name){
        Texture texture=new Texture(name+".png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return new TextureRegion(texture);
    }

    @Override
    public void buildUI() {
        Group group=new Group();
        group.setTouchable(Touchable.enabled);
        group.setSize(width,height);

        Texture columnTexture=new Texture("water_column.png");
        columnTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture particleTexture=new Texture("particle.png");
        particleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        water=new Water(new TextureRegion(columnTexture),new TextureRegion(particleTexture),100);
        water.setSize(width,height);

        water.setTouchable(Touchable.enabled);
        water.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.error("Input X",x+"");
                water.splash(x,-200);
                return true;
            }
        });

        stage.addActor(water);
    }

    private void showHorizontalSweep(TextureRegion sweepRegion,float x,float y){
        Vector2 from=new Vector2(0,y);
        Vector2 to=new Vector2(width,y);
        Vector2 origin=new Vector2(x,y);

        HorizontalSweep horizontalSweep=new HorizontalSweep(sweepRegion,width*0.1f,from,to,origin,Color.valueOf("FF77AA88"),3);
        horizontalSweep.show(stage);
    }

    private void showVerticalSweep(TextureRegion sweepRegion,float x,float y){
        Vector2 from=new Vector2(x,0);
        Vector2 to=new Vector2(x,height);
        Vector2 origin=new Vector2(x,y);

        VerticalSweep horizontalSweep=new VerticalSweep(sweepRegion,width*0.1f,from,to,origin,Color.valueOf("FF77AA"),3);
        horizontalSweep.show(stage);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
