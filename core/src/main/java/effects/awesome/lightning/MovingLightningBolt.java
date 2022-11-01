package effects.awesome.lightning;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovingLightningBolt extends SimpleLightningBolt {
    private Vector2 tangent;
    private Vector2 normal;
    private float length;
    private List<Float> positions;
    private float sway,jaggedness;
    private Vector2 prevPoint;
    private float prevDisplacement;
    private int currentIndex;
    private boolean startFadeOut;
    private float boltSpeed;
    public MovingLightningBolt(Vector2 source, Vector2 dest, Color color, Texture halfCircle, Texture lineSegment) {
        super(source, dest, color, halfCircle, lineSegment);
        startFadeOut=false;
        this.boltSpeed=0.2f;
        setInterpolation(Interpolation.linear);
        setFadeDuration(1.0f);
        setMaxAlpha(1.0f);
    }

    @Override
    protected void createBolt() {
        tangent = new Vector2(dest).sub(source);

        normal = new Vector2(tangent.y, -tangent.x).nor();
        length = tangent.len();

        positions = new ArrayList<Float>();
        positions.add(0.0f);

        for (int i = 0; i < length /10f; i++)
            positions.add(MathUtils.random(0.0f, 1.0f));

        Collections.sort(positions);

        sway = 100.0f;
        jaggedness = 1 / sway;

        prevPoint = source;
        prevDisplacement = 0;
        currentIndex=1;
        createNextBolt();
    }

    protected void createNextBolt(){
        if (currentIndex<positions.size()){
            float pos = positions.get(currentIndex);

            // used to prevent sharp angles by ensuring very close positions also have small perpendicular variation.
            float scale = (length * jaggedness) * (pos - positions.get(currentIndex-1));

            // defines an envelope. Points near the middle of the bolt can be further from the central line.
            float envelope = pos > 0.95f ? 20 * (1 - pos) : 1;

            float displacement = MathUtils.random(-sway,sway);
            displacement -= (displacement - prevDisplacement) * (1 - scale);
            displacement *= envelope;

            Vector2 point=new Vector2(source).add(new Vector2(tangent).scl(pos)).add(new Vector2(normal).scl(displacement));
            addActor(new LightningLine(lineSegment,halfCircle,prevPoint, point, thickness,color));
            prevPoint = point;
            prevDisplacement = displacement;
            currentIndex++;
            alpha=(float) currentIndex/(float) positions.size();
        }else {
            alpha=1.0f;
            addActor(new LightningLine(lineSegment,halfCircle,prevPoint, dest, thickness,color));
        }
        clampAlpha();

    }

    @Override
    protected void updateLightning(float delta) {
        if (currentIndex<positions.size()){
            for (int i=0;i<boltSpeed/delta;i++) {
                if (currentIndex<positions.size()) {
                    createNextBolt();
                }else{
                    super.updateLightning(delta);
                }
            }
        }else{
            super.updateLightning(delta);
        }
    }
}
