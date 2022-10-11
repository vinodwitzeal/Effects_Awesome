package effects.awesome.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;

import effects.awesome.ScreenController;
import effects.awesome.lightning.MovingLightningBolt;
import effects.awesome.lightning.SimpleLightningBolt;
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

    @Override
    public void buildUI() {
        Group group=new Group();
        group.setTouchable(Touchable.enabled);
        group.setSize(width,height);
//        stage.addActor(group);

        Texture lineSegment=new Texture("line_segment.png");
        lineSegment.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture halfCircle=new Texture("half_circle.png");
        halfCircle.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        group.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Vector2 source=new Vector2(width/2f,height/2f);
                Vector2 des=new Vector2(x,y);
                Color color=new Color(MathUtils.random(0.0f,1.0f),MathUtils.random(0.0f,1.0f),MathUtils.random(0.0f,1.0f),1.0f);
                group.addAction(Actions.repeat(MathUtils.random(5,10),Actions.delay(0.05f,Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                int maxBolts=MathUtils.random(1,1);
                                for (int i=0;i<maxBolts;i++) {
                                    SimpleLightningBolt lightningBolt = new MovingLightningBolt(source, des, color, halfCircle, lineSegment);
                                    lightningBolt.setSize(width, height);
                                    group.addActor(lightningBolt);
                                }
                            }
                        });
                    }
                }))));

                return true;
            }
        });

        Texture columnTexture=new Texture("water_column.png");
        columnTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture particleTexture=new Texture("particle.png");
        particleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        water=new Water(new TextureRegion(columnTexture),new TextureRegion(particleTexture),100);
        water.setSize(width,height);
        stage.addActor(water);

        water.setTouchable(Touchable.enabled);
        water.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Vector2 source=new Vector2(x,height/2f);
                Vector2 des=new Vector2(x,water.getWaterHeight(x));
                Color color=new Color(MathUtils.random(0.0f,1.0f),MathUtils.random(0.0f,1.0f),MathUtils.random(0.0f,1.0f),1.0f);
                group.addAction(Actions.repeat(MathUtils.random(2,5),Actions.delay(0.05f,Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                int maxBolts=MathUtils.random(1,1);
                                for (int i=0;i<maxBolts;i++) {
                                    SimpleLightningBolt lightningBolt = new MovingLightningBolt(source, des, color, halfCircle, lineSegment);
                                    lightningBolt.setSize(width, height);
                                    stage.addActor(lightningBolt);
                                }
                            }
                        });
                    }
                }))));

                Gdx.app.error("Input X",x+"");
                water.splash(x,-400);
                return true;
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
