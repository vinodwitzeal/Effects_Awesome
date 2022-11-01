package effects.awesome.lightning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.BloomEffect;
import com.crashinvaders.vfx.effects.LevelsEffect;
import com.crashinvaders.vfx.effects.VfxEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import effects.awesome.lightning.LightningLine;

public class SimpleLightningBolt extends Group {
    protected float alpha;
    private float maxAlpha;
    private float fadeDuration;
    private float currentTime;
    protected final Vector2 source,dest;
    protected final Color color;
    protected final Texture halfCircle,lineSegment;
    protected float thickness;
    private Interpolation interpolation;
    private VfxManager vfxManager;
    private VfxEffect effect;
    public SimpleLightningBolt(Vector2 source, Vector2 dest, Color color, Texture halfCircle, Texture lineSegment){
        maxAlpha=1.0f;
        fadeDuration=0.5f;
        currentTime=0.0f;
        this.source=source;
        this.dest=dest;
        this.color=color;
        this.halfCircle=halfCircle;
        this.lineSegment=lineSegment;
        this.thickness=2.0f;
        this.interpolation=Interpolation.linear;
        vfxManager=new VfxManager(Pixmap.Format.RGBA8888);
        effect=new BloomEffect();
        createBolt();
    }

    public void setInterpolation(Interpolation interpolation){
        this.interpolation=interpolation;
    }

    public void setThickness(float thickness){
        this.thickness=thickness;
    }

    public void setFadeDuration(float fadeDuration){
        this.fadeDuration=fadeDuration;
    }

    public void setMaxAlpha(float maxAlpha){
        this.maxAlpha=maxAlpha;
    }

    protected void createBolt() {
        Vector2 tangent = new Vector2(dest).sub(source);

        Vector2 normal = new Vector2(tangent.y, -tangent.x).nor();
        float length = tangent.len();

        List<Float> positions = new ArrayList<Float>();
        positions.add(0.0f);

        for (int i = 0; i < length / 10; i++)
            positions.add(MathUtils.random(0.0f, 1.0f));

        Collections.sort(positions);

        float sway = 160.0f;
        float jaggedness = 1 / sway;

        Vector2 prevPoint = source;
        float prevDisplacement = 0;
        for (int i = 1; i < positions.size(); i++) {
            float pos = positions.get(i);

            // used to prevent sharp angles by ensuring very close positions also have small perpendicular variation.
            float scale = (length * jaggedness) * (pos - positions.get(i-1));

            // defines an envelope. Points near the middle of the bolt can be further from the central line.
            float envelope = pos > 0.95f ? 20 * (1 - pos) : 1;

            float displacement = MathUtils.random(-sway,sway);
            displacement -= (displacement - prevDisplacement) * (1 - scale);
            displacement *= envelope;

            Vector2 point=new Vector2(source).add(new Vector2(tangent).scl(pos)).add(new Vector2(normal).scl(displacement));
            addActor(new LightningLine(lineSegment,halfCircle,prevPoint, point, thickness,color));
            prevPoint = point;
            prevDisplacement = displacement;
        }
        addActor(new LightningLine(lineSegment,halfCircle,prevPoint, dest, thickness,color));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateLightning(delta);
    }

    protected void updateLightning(float delta){
        alpha=maxAlpha-maxAlpha*interpolation.apply(currentTime/fadeDuration);
        currentTime+=delta;
        if (currentTime>=fadeDuration){
            currentTime=fadeDuration;
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        remove();
                    }
                });
        }
        clampAlpha();

    }

    protected void clampAlpha(){
        alpha=MathUtils.clamp(alpha,0.0f,maxAlpha);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.flush();

//        vfxManager.cleanUpBuffers();
//        vfxManager.beginInputCapture();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        super.draw(batch, parentAlpha);
        batch.flush();
//        vfxManager.endInputCapture();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        vfxManager.applyEffects();
//        vfxManager.renderToScreen();
    }

    @Override
    protected void drawChildren(Batch batch, float parentAlpha) {
        if (alpha<=0.0f)return;
        super.drawChildren(batch, alpha);
    }
}
