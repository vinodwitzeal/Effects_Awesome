package effects.awesome.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import effects.awesome.ui.fonts.Font;
import effects.awesome.ui.fonts.FontPool;
import effects.awesome.ui.fonts.FontScaler;
import effects.awesome.ui.fonts.FontType;

public class UILabel extends Label{
    private final UILabelStyle uiLabelStyle;
    public UILabel(CharSequence text,UILabelStyle uiLabelStyle) {
        super(text, uiLabelStyle);
        this.uiLabelStyle=uiLabelStyle;
        setFontScale(uiLabelStyle.fontScale);
//        layout();
//        invalidateHierarchy();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        uiLabelStyle.apply();
        super.draw(batch, parentAlpha);
    }

    public static class UILabelStyle extends LabelStyle{
        private final Font uiFont;
        private final float fontScale;
        public UILabelStyle(FontType type, float size, Color fontColor, Drawable background){
            uiFont= FontPool.obtain(type);
            this.fontScale=size/16.0f;
            this.font=uiFont;
            this.fontColor=fontColor;
            this.background=background;
        }

        public UILabelStyle(FontType type,float size){
            this(type,size,Color.WHITE,null);
        }

        public UILabelStyle(FontType type){
            this(type,16.0f);
        }

        public void apply(){
            uiFont.apply(fontScale);
        }


        public void reset(){
            uiFont.reset();
        }
    }
}
