package effects.awesome.ui.fonts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

public class Font extends DistanceFieldFont {
    private final String SMOOTHING = "u_smoothing";
    private final String ENABLE_SHADOW = "enableShadow", SHADOW_OFFSET = "u_shadow", SHADOW_COLOR = "u_shadowColor";
    private final String ENABLE_OUTLINE = "enableOutline", OUTLINE_WIDTH = "u_outline", OUTLINE_COLOR = "u_outlineColor";
    private ShaderProgram defaultShader, distanceShader;
    private float smoothing;
    private int enableShadow,enableOutline;
    private Vector2 shadow;
    private float outline;
    private Color shadowColor,outlineColor;
    public Font(BitmapFontData data, TextureRegion region,ShaderProgram distanceShader) {
        super(data, region, false);
        this.distanceShader=distanceShader;
        this.enableShadow=0;
        this.enableOutline=0;
        this.shadow=new Vector2(0,0);
        this.outline=0.0f;
        this.shadowColor=Color.valueOf("000000");
        this.outlineColor=Color.valueOf("000000");
    }

    public void apply(float fontScale){
        this.smoothing=fontScale/4.0f;
    }

    public void reset(){
        getData().setScale(1.0f);
    }

    @Override
    public BitmapFontCache newFontCache() {
        return new FontCache(this, false);
    }

    private class FontCache extends BitmapFontCache {
        private FontCache(BitmapFont font, boolean integer) {
            super(font, integer);
        }

        private void setShader(Batch batch) {
            defaultShader = batch.getShader();
            batch.flush();
            batch.setShader(distanceShader);
            distanceShader.setUniformf(SMOOTHING, smoothing);
            distanceShader.setUniformi(ENABLE_OUTLINE, enableOutline);
            distanceShader.setUniformi(ENABLE_SHADOW, enableShadow);
            distanceShader.setUniformf(OUTLINE_WIDTH, outline);
            distanceShader.setUniformf(OUTLINE_COLOR, outlineColor);
            distanceShader.setUniformf(SHADOW_OFFSET, shadow);
            distanceShader.setUniformf(SHADOW_COLOR, shadowColor);
        }

        private void removeShader(Batch batch) {
            batch.flush();
            batch.setShader(defaultShader);
        }

        @Override
        public void draw(Batch spriteBatch) {
            setShader(spriteBatch);
            super.draw(spriteBatch);
            removeShader(spriteBatch);
        }

        @Override
        public void draw(Batch spriteBatch, int start, int end) {
            setShader(spriteBatch);
            super.draw(spriteBatch, start, end);
            removeShader(spriteBatch);
        }

        @Override
        public void draw(Batch spriteBatch, float alphaModulation) {
            setShader(spriteBatch);
            super.draw(spriteBatch, alphaModulation);
            removeShader(spriteBatch);
        }
    }
}
