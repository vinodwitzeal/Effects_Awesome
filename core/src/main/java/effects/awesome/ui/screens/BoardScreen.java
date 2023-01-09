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



//        stage.addActor(group);

        Texture blurCircle=new Texture("blur_circle.png");
        blurCircle.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion blurCircleRegion=new TextureRegion(blurCircle);

        List<TextureRegion> piecesRegion=new ArrayList<>();
        piecesRegion.add(findRegion("candy_piece_1"));
        piecesRegion.add(findRegion("candy_piece_2"));
        piecesRegion.add(findRegion("candy_piece_3"));
        piecesRegion.add(findRegion("candy_piece_4"));

        Texture lineSegment=new Texture("line_segment.png");
        lineSegment.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture halfCircle=new Texture("half_circle.png");
        halfCircle.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture sweepTexture=new Texture("line_faded.png");
        sweepTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion sweepRegion=new TextureRegion(sweepTexture);

        group.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

//                float sweepWidth=MathUtils.random(width/2f,width);
//                float sweepHeight=MathUtils.random(height*0.05f,height*0.1f);
//                float sweepX=(width-sweepWidth)/2f;
//                float sweepY=y;
//
//                Color lineColor=new Color(1,1,1,0.5f);
//                Sweep sweep=new HorizontalSweep(sweepRegion,lineColor);
//                sweep.setSize(sweepWidth,sweepHeight);
//                sweep.setPosition(sweepX,sweepY);
//                sweep.show(group);
//
//                Vector2 source=new Vector2(width/2f,height/2f);
//                Vector2 des=new Vector2(x,y);
//                Color color=new Color(MathUtils.random(0.0f,1.0f),MathUtils.random(0.0f,1.0f),MathUtils.random(0.0f,1.0f),1.0f);
//                group.addAction(Actions.repeat(MathUtils.random(2,5),Actions.delay(0.05f,Actions.run(new Runnable() {
//                    @Override
//                    public void run() {
//                        Gdx.app.postRunnable(new Runnable() {
//                            @Override
//                            public void run() {
//                                int maxBolts=MathUtils.random(1,1);
//                                for (int i=0;i<maxBolts;i++) {
//                                    SimpleLightningBolt lightningBolt = new MovingLightningBolt(source, des, color, halfCircle, lineSegment);
//                                    lightningBolt.setSize(width, height);
//                                    group.addActor(lightningBolt);
//
//
//
//                                }
//                            }
//                        });
//                    }
//                }))));

                CandyBreak simpleExplosion=new CandyBreak(blurCircleRegion,piecesRegion);
                simpleExplosion.setSize(width*0.2f,width*0.2f);
                simpleExplosion.setPosition(x-simpleExplosion.getWidth()/2f,y-simpleExplosion.getHeight()/2f);
                simpleExplosion.setPieceColor(Color.valueOf("FF0044"));
                group.addActor(simpleExplosion);
                simpleExplosion.explode();

                showHorizontalSweep(sweepRegion,x,y);
                showVerticalSweep(sweepRegion,x,y);


                return true;
            }
        });

        stage.addActor(group);

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


        EqualTransformGroup transformGroup=new EqualTransformGroup();
        Image image=new Image(new Texture("bg_pool.png"));
        image.setSize(width,height);
        transformGroup.setSize(width,height);
        transformGroup.setOrigin(width/2f,height/2f);
        transformGroup.child.addActor(image);
        transformGroup.setTransform(true);
        stage.addActor(transformGroup);
        transformGroup.setShearY(1.0f);

//        TransformGroup transformGroup2=new TransformGroup();
//        Image image2=new Image(new Texture("bg_pool.png"));
//        image2.setSize(width,height);
//        transformGroup2.setSize(width,height);
//        transformGroup2.addActor(image2);
//        transformGroup2.setTransform(true);
//        stage.addActor(transformGroup2);
//        transformGroup2.setShear(0,-0.5f);
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
