package effects.awesome.ui.fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FontPool {
    private final Map<String, Font> fonts;
    private final Map<String, BitmapFont.BitmapFontData> fontDataMap;
    private final Map<String, Texture> textureMap;
    private ShaderProgram shaderProgram;

    private FontPool() {
        fonts = Collections.synchronizedMap(new HashMap<>());
        fontDataMap = Collections.synchronizedMap(new HashMap<>());
        textureMap = Collections.synchronizedMap(new HashMap<>());
        shaderProgram = new ShaderProgram(Gdx.files.internal("shaders/distance.vert"), Gdx.files.internal("shaders/distance.frag"));
        if (!shaderProgram.isCompiled()) {
            throw new RuntimeException("Font shader not compiled");
        }
    }

    private Font getFont(FontType type) {
        String fontName = type.getName();
        if (fonts.containsKey(fontName)) {
            return fonts.get(fontName);
        } else {
            BitmapFont.BitmapFontData fontData = getFontData(fontName);
            TextureRegion region = new TextureRegion(getTexture(fontName));
            Font font = new Font(fontData, region, shaderProgram);
            fonts.put(fontName, font);
            return font;
        }
    }

    private BitmapFont.BitmapFontData getFontData(String name) {
        if (fontDataMap.containsKey(name)) {
            return fontDataMap.get(name);
        } else {
            BitmapFont.BitmapFontData fontData = new BitmapFont.BitmapFontData(Gdx.files.internal("fonts/" + name + ".fnt"), false);
            fontDataMap.put(name, fontData);
            return fontData;
        }
    }

    private Texture getTexture(String fontName) {
        if (textureMap.containsKey(fontName)) {
            return textureMap.get(fontName);
        } else {
            Texture texture = new Texture("fonts/" + fontName + ".png");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureMap.put(fontName, texture);
            return texture;
        }
    }

    private void disposeAll() {
        for (Font font : fonts.values()) {
            font.dispose();
        }
        shaderProgram.dispose();
    }

    private static FontPool instance;

    private static FontPool instance() {
        if (instance == null) {
            instance = new FontPool();
        }
        return instance;
    }

    public static Font obtain(FontType type) {
        return instance().getFont(type);
    }

    public static void dispose() {
        if (instance != null) {
            instance.disposeAll();
        }
        instance = null;
    }
}
