package effects.awesome.lightning;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LightningLine extends Actor {
    private final Vector2 pointA,pointB;
    private final Vector2 tangent=new Vector2();
    private final float thickness;
    private float imageThickness;
    private TextureRegion lineSegmentRegion;
    private TextureRegion halfCircleRegion;
    private float lineSegmentWidth,lineSegmentHeight;
    private float halfCircleWidth,halfCircleHeight;
    private float rotation;
    private float thicknessScale;
    private Vector2 capOrigin;
    private Vector2 middleOrigin;
    private Vector2 middleScale;
    public LightningLine(){
        this(new Texture("line_segment.png"),new Texture("half_circle.png"),new Vector2(),new Vector2(),1.0f,Color.WHITE);
    }

    public LightningLine(Texture lineSegment,Texture halfCircle,Vector2 pointA,Vector2 pointB,float thickness,Color color) {
        this.pointA = new Vector2(pointA);
        this.pointB = new Vector2(pointB);
        this.thickness = thickness;
        this.imageThickness=16.0f;
        this.lineSegmentWidth=lineSegment.getWidth();
        this.lineSegmentHeight=lineSegment.getHeight();
        this.halfCircleWidth=halfCircle.getWidth();
        this.halfCircleHeight=halfCircle.getHeight();

        this.lineSegmentRegion=new TextureRegion(lineSegment);
        this.halfCircleRegion=new TextureRegion(halfCircle);
        setColor(color);

        tangent.set(pointB).sub(pointA);
        rotation = MathUtils.atan2(tangent.y, tangent.x)*MathUtils.radiansToDegrees;

        thicknessScale = thickness / imageThickness;


        capOrigin = new Vector2(halfCircleWidth, halfCircleHeight/2f);
        middleOrigin = new Vector2(0, lineSegmentHeight/2f);
        middleScale = new Vector2(tangent.len(), thicknessScale);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isVisible()){
            Color color=getColor();
            batch.setColor(color.r,color.g,color.b,color.a*parentAlpha);
            batch.draw(lineSegmentRegion,pointA.x,pointA.y-lineSegmentHeight/2f,middleOrigin.x,middleOrigin.y,1,lineSegmentHeight,middleScale.x,middleScale.y,rotation);
            batch.draw(halfCircleRegion,pointA.x-halfCircleWidth,pointA.y-halfCircleHeight/2f,capOrigin.x,capOrigin.y,halfCircleWidth,halfCircleHeight,thicknessScale,thicknessScale,rotation);
            batch.draw(halfCircleRegion,pointB.x-halfCircleWidth,pointB.y-halfCircleHeight/2f,capOrigin.x,capOrigin.y,halfCircleWidth,halfCircleHeight,thicknessScale,thicknessScale,rotation+180);
        }
    }
}
