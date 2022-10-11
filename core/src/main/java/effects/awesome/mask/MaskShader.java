package effects.awesome.mask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.graphics.glutils.HdpiMode;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class MaskShader {
    private static MaskShader instance;

    public static MaskShader instance() {
        if (instance == null) {
            instance = new MaskShader();
        }
        return instance;
    }


    private String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
            + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "uniform mat4 u_projTrans;\n" //
            + "varying vec4 v_color;\n" //
            + "varying vec2 v_texCoords;\n" //
            + "\n" //
            + "void main()\n" //
            + "{\n" //
            + "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
            + "   v_color.a = v_color.a * (255.0/254.0);\n" //
            + "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "}\n";
    private String fragmentShader = "#ifdef GL_ES\n" //
            + "#define LOWP lowp\n" //
            + "precision mediump float;\n" //
            + "#else\n" //
            + "#define LOWP \n" //
            + "#endif\n" //
            + "varying LOWP vec4 v_color;\n" //
            + "varying vec2 v_texCoords;\n" //
            + "uniform sampler2D u_texture;\n" //
            + "uniform sampler2D u_mask;\n" //
            + "void main()\n"//
            + "{\n" //
            + "  vec4 resultColor = v_color * texture2D(u_texture, v_texCoords);\n" //
            + "  float alpha=texture2D(u_mask,v_texCoords).a;\n" //
            + "  gl_FragColor = vec4(resultColor.rgb,resultColor.a*alpha);\n" //
            + "}";

    private ShaderProgram defaultShader, maskShader;
    private boolean compiled;
    private FrameBuffer maskBuffer, actorBuffer, resultBuffer;
    private final Vector2 temp = new Vector2();

    private MaskShader() {
        maskShader = new ShaderProgram(vertexShader, fragmentShader);
        compiled = maskShader.isCompiled();
        if (!compiled) {
            Gdx.app.error("MaskShader", maskShader.getLog());
        }
        maskBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
        actorBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
        resultBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
    }

    public void mask(Batch batch, TextureRegion mask, float parentAlpha, ActorDrawer actorDrawer) {
        if (compiled) {
            Actor actor = actorDrawer.getActor();
            batch.end();
            drawMask(batch, mask, actor);
            drawActor(batch, actorDrawer, actor);
            drawResult(batch, actor);
            batch.begin();
            renderBuffer(resultBuffer,batch);
        }
    }

    public void draw(Batch batch, float parentAlpha,Actor actor) {
        /*batch.begin();
        Color color=actor.getColor();
        temp.set(0,0);
        float width=actor.getWidth();
        float height=actor.getHeight();
        actor.localToStageCoordinates(temp);
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        Texture texture = resultBuffer.getColorBufferTexture();
        batch.draw(texture,temp.x,temp.y,width,height,(int)temp.x,(int)(temp.y),(int)width,(int)height,false,false);*/
    }

    public boolean isCompiled() {
        return compiled;
    }

    private void drawMask(Batch batch, TextureRegion mask, Actor actor) {
        maskBuffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        temp.set(0, 0);
        actor.localToStageCoordinates(temp);
        batch.draw(mask, temp.x, temp.y, actor.getOriginX(), actor.getOriginY(), actor.getWidth(), actor.getHeight(), actor.getScaleX(), actor.getScaleY(), actor.getRotation());
        batch.end();
        maskBuffer.end();
    }

    private void drawActor(Batch batch, ActorDrawer actorDrawer, Actor actor) {
        actorBuffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        actorDrawer.draw(batch);
        batch.end();
        actorBuffer.end();
    }

    private void drawResult(Batch batch, Actor actor) {
        resultBuffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        defaultShader = batch.getShader();
        batch.begin();
        batch.setShader(maskShader);

        Texture maskTexture = maskBuffer.getColorBufferTexture();
        maskTexture.bind(1);
        maskShader.setUniformi("u_mask", 1);
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        batch.draw(actorBuffer.getColorBufferTexture(), 0, 0, actorBuffer.getWidth(), actorBuffer.getHeight());
        batch.end();
        batch.setShader(defaultShader);
        resultBuffer.end();
    }

    private void renderBuffer(FrameBuffer frameBuffer, Batch batch) {
        Texture texture = frameBuffer.getColorBufferTexture();
        TextureRegion textureRegion = new TextureRegion(texture);
//        textureRegion.flip(false,false);
        batch.draw(textureRegion, 0, 0);
    }


    public interface ActorDrawer {
        Actor getActor();

        void draw(Batch batch);
    }
}
