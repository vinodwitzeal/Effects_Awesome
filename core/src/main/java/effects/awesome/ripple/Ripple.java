package effects.awesome.ripple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class Ripple extends Actor {
    private Texture backgroundTexture,noiseTexture;
    private Sprite sprite;
    private ShaderProgram rippleShader;
    private float time;
    private Vector2 noiseScrollVelocity;
    public Ripple(Texture backgroundTexture,Texture noiseTexture){
        this.backgroundTexture=backgroundTexture;
        this.noiseTexture=noiseTexture;
        this.backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        this.backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.noiseTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite=new Sprite(backgroundTexture);

        rippleShader =new ShaderProgram(Gdx.files.internal("shaders/ripple.vert"),Gdx.files.internal("shaders/ripple.frag"));
        noiseScrollVelocity=new Vector2(0.5f,0.01f);
        if (!rippleShader.isCompiled()){
            throw new RuntimeException("Shaders Error"+ rippleShader.getLog());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time+=delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.flush();
        ShaderProgram shaderProgram=batch.getShader();
        batch.setShader(rippleShader);
        noiseTexture.bind();
        rippleShader.setUniformf("u_noise_scale", 0.005f);
        rippleShader.setUniformf("u_noise_scroll_velocity", noiseScrollVelocity);
        rippleShader.setUniformf("u_distortion", 0.005f);
        rippleShader.setUniformf("u_time", time);
        batch.draw(backgroundTexture,getX(),getY(),getWidth(),getHeight());
        batch.flush();
        batch.setShader(shaderProgram);
    }

    public void dispose(){
        rippleShader.dispose();
        noiseTexture.dispose();
        backgroundTexture.dispose();
    }
}
