package effects.awesome.explosion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CandyBreak extends Group {
    private TextureRegion blurCircle;
    private List<TextureRegion> piecesRegion;
    private int minParticles,maxParticles;
    private Color color;
    public CandyBreak(TextureRegion blurCircle, List<TextureRegion> piecesRegion){
        this.blurCircle=blurCircle;
        this.piecesRegion=piecesRegion;
        this.minParticles=5;
        this.maxParticles=10;
        this.color= Color.valueOf("FFFFFF");
    }

    public void setMinParticles(int minParticles) {
        this.minParticles = minParticles;
    }

    public void setMaxParticles(int maxParticles) {
        this.maxParticles = maxParticles;
    }

    public void setPieceColor(Color color){
        this.color=color;
    }


    public void explode(){
        float width=getWidth();
        float height=getHeight();
        Image blurImage=new Image(blurCircle);
        blurImage.setColor(Color.valueOf("cff0ff"));
        blurImage.setSize(width,height);
        blurImage.setScale(1.5f);
        blurImage.setOrigin(width/2f,height/2f);
        blurImage.addAction(
                Actions.sequence(
                        Actions.fadeOut(0),
                        Actions.parallel(
                                Actions.fadeIn(0.5f),
                                Actions.scaleTo(0,0,0.5f)
                        )
                )
        );
        addActor(blurImage);

        addAction(Actions.sequence(
                Actions.delay(0.4f,Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                showPieces();
                            }
                        });
                    }
                })),
                Actions.delay(1.0f,Actions.removeActor())
        ));
    }

    private void showPieces(){
        float width=getWidth();
        float height=getHeight();
        int totalParticles= MathUtils.random(minParticles,maxParticles);
        float centerX=width/2f;
        float centerY=height/2f;
        for (int i=0;i<totalParticles;i++){
            Collections.shuffle(piecesRegion);
            float pieceSize=MathUtils.random(width*0.2f,width*0.2f);
            float startX=centerX-pieceSize/2f;
            float startY=centerY-pieceSize/2f;
            CandyPiece candyPiece=new CandyPiece(piecesRegion.get(0));
            candyPiece.setSize(pieceSize,pieceSize);
            candyPiece.setOrigin(pieceSize/2f,pieceSize/2f);
            candyPiece.setScaling(Scaling.fit);
            candyPiece.setColor(color);
            candyPiece.setPosition(startX,startY);

            float distance=MathUtils.random(width*0.25f,width*0.5f);
            float angle=MathUtils.random(0,360f);
            Vector2 velocity=new Vector2(MathUtils.cosDeg(angle),MathUtils.sinDeg(angle)).nor().scl(width);
            candyPiece.velocity.set(velocity);
            candyPiece.gravity=-width;
            float maxScale=MathUtils.random(1.0f,1.5f);
            candyPiece.setScale(0);
            candyPiece.addAction(Actions.parallel(
                    Actions.sequence(
                            Actions.scaleTo(maxScale,maxScale,0.2f),
                            Actions.scaleTo(0,0,0.3f)
                    ),
                    Actions.rotateBy(MathUtils.random(-360f,360f),0.5f)
            ));
            addActor(candyPiece);
        }
    }

    private class CandyPiece extends Image {
        private Vector2 velocity;
        private float duration;
        private float current;
        private boolean completed;
        private float gravity;
        public CandyPiece(TextureRegion region){
            super(region);
            this.velocity=new Vector2();
            this.gravity=-9.8f;
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            current+=delta;
            if (current>=duration){
                current=duration;
                completed=true;
            }
            setX(getX()+velocity.x*delta);
            setY(getY()+velocity.y*delta+0.5f*gravity*delta*delta);
            velocity.y=velocity.y+gravity*delta;
        }
    }
}
