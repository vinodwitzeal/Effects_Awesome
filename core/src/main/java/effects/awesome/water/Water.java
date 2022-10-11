package effects.awesome.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class Water extends Actor {
    private TextureRegion waterRegion;
    private TextureRegion particleRegion;
    private float waterU, waterU2, waterV, waterV2;
    private float[] waterVertices = new float[20];
    private float particleWidth, particleHeight;
    private float particleOriginX, particleOriginY;
    private List<WaterColumn> columns;

    public float tension = 0.025f;
    public float dampening = 0.025f;
    public float spread = 0.25f;
    private List<WaterParticle> particles;
    private List<WaterParticle> invalidParticles;
    private float thickness = 20.0f;
    private Color colorLightBlue;
    private Color colorNightBlue;
    private float colorFadedLightBlue;
    private float colorFadeNightBlue;
    private float colorWhite, colorTransparent;
    private FrameBuffer metaBallTarget, particleTarget;
    private TextureRegion metaBallTargetRegion, particleTargetRegion;

    public Water(TextureRegion waterRegion, TextureRegion particleRegion, int columnCount) {
        this.waterRegion = waterRegion;
        this.waterU = waterRegion.getU();
        this.waterU2 = waterRegion.getU2();
        this.waterV = waterRegion.getV();
        this.waterV2 = waterRegion.getV2();
        this.particleRegion = particleRegion;
        this.particleWidth = particleRegion.getRegionWidth();
        this.particleHeight = particleRegion.getRegionHeight();
        this.particleOriginX = particleWidth / 2f;
        this.particleOriginY = particleHeight / 2f;
        this.columns = new ArrayList<WaterColumn>();
        this.particles = new ArrayList<WaterParticle>();
        this.invalidParticles = new ArrayList<WaterParticle>();
        for (int i = 0; i < columnCount; i++) {
            columns.add(new WaterColumn(240, 240, 0));
        }

        this.colorLightBlue = Color.valueOf("#19c6ff");
        this.colorFadedLightBlue = new Color(colorLightBlue).mul(0.8f).toFloatBits();
        this.colorNightBlue = Color.valueOf("#002666");
        this.colorFadeNightBlue = new Color(colorNightBlue).mul(0.9f).toFloatBits();

        this.colorWhite = Color.valueOf("#ffffff").toFloatBits();
        this.colorTransparent = Color.valueOf("#00000000").toFloatBits();

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        metaBallTarget = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, true);
        particleTarget = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, true);

        metaBallTargetRegion = new TextureRegion(metaBallTarget.getColorBufferTexture());
        metaBallTargetRegion.flip(false, true);

        particleTargetRegion = new TextureRegion(particleTarget.getColorBufferTexture());
        particleTargetRegion.flip(false, true);

    }

    public float getWaterHeight(float x) {
        if (x < 0 || x > getWidth())
            return 240;

        return columns.get((int) (x / getScale())).height;
    }

    void updateParticle(WaterParticle particle) {
        float gravity = 0.3f;
        particle.velocity.y -= gravity;
        particle.position.add(particle.velocity);
        particle.orientation = getAngle(particle.velocity);
    }

    public void splash(float xPosition, float speed) {
        int index = (int) MathUtils.clamp(xPosition / getScale(), 0, columns.size() - 1);
        for (int i = Math.max(0, index); i < Math.min(columns.size() - 1, index + 1); i++)
            columns.get(index).speed = speed;
        createSplashParticle(xPosition, speed);
    }

    private void createSplashParticle(float xPosition, float speed) {
        float y = getWaterHeight(xPosition);
        speed = Math.abs(speed);
        if (speed > 120) {
            for (int i = 0; i < speed / 8; i++) {
                Vector2 pos = new Vector2(xPosition, y).add(getRandomVector(40));
                Vector2 vel = fromPolar(MathUtils.random(30, 150), MathUtils.random(0, 0.5f * (float) Math.sqrt(speed)));
                createParticle(pos, vel);
            }
        }
    }

    private void createParticle(Vector2 pos, Vector2 velocity) {
        particles.add(new WaterParticle(pos, velocity, 0));
    }

    private Vector2 fromPolar(float angle, float magnitude) {
        return new Vector2(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle)).scl(magnitude);
    }


    private Vector2 getRandomVector(float maxLength) {
        return fromPolar(MathUtils.random(0, 180), MathUtils.random(0, maxLength));
    }

    private float getAngle(Vector2 vector) {
        return MathUtils.atan2(vector.y, vector.x) * MathUtils.radiansToDegrees;
    }

    private float getScale() {
        return getWidth() / (columns.size() - 1);
    }

    @Override
    public void act(float delta) {
        for (int i = 0; i < columns.size(); i++)
            columns.get(i).update(dampening, tension);

        float[] lDeltas = new float[columns.size()];
        float[] rDeltas = new float[columns.size()];

        // do some passes where columns pull on their neighbours
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < columns.size(); i++) {
                if (i > 0) {
                    lDeltas[i] = spread * (columns.get(i).height - columns.get(i - 1).height);
                    columns.get(i - 1).speed += lDeltas[i];
                }
                if (i < columns.size() - 1) {
                    rDeltas[i] = spread * (columns.get(i).height - columns.get(i + 1).height);
                    columns.get(i + 1).speed += rDeltas[i];
                }
            }

            for (int i = 0; i < columns.size(); i++) {
                if (i > 0)
                    columns.get(i - 1).height += lDeltas[i];
                if (i < columns.size() - 1)
                    columns.get(i + 1).height += rDeltas[i];
            }
        }

        for (WaterParticle particle : particles) {
            updateParticle(particle);
        }

        invalidParticles.clear();
        for (WaterParticle particle : particles) {
            Vector2 position = particle.position;
            if (position.x < getX() || position.x > getWidth() || position.y < getWaterHeight(position.x)) {
                invalidParticles.add(particle);
            }
        }

        particles.removeAll(invalidParticles);
        invalidParticles.clear();

//        particles = particles.Where(x => x.Position.X >= 0 && x.Position.X <= 800 && x.Position.Y - 5 <= GetHeight(x.Position.X)).ToList();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawToTargets(batch, parentAlpha);
        drawWater(batch,parentAlpha);
    }

    private void drawWater(Batch batch,float parentAlpha){
        batch.setColor(new Color(0.8f, 0.8f, 0.8f, 0.8f));
        batch.draw(particleTargetRegion, -1, -1);
        batch.setColor(colorNightBlue);
        batch.draw(particleTargetRegion, 1, 1);
        batch.setColor(colorLightBlue);
        batch.draw(particleTargetRegion, 0, 0);

        float bottom = getY();
        float scale = getScale();
        for (int i = 1; i < columns.size(); i++) {
            Vector2 p1 = new Vector2((i - 1) * scale, columns.get(i - 1).height);
            Vector2 p2 = new Vector2(i * scale, columns.get(i).height);
            Vector2 p3 = new Vector2(p2.x, bottom);
            Vector2 p4 = new Vector2(p1.x, bottom);

            setWaterVertex(0, p1, colorFadedLightBlue, waterU, waterV);
            setWaterVertex(5, p2, colorFadedLightBlue, waterU2, waterV);
            setWaterVertex(10, p3, colorFadeNightBlue, waterU2, waterV2);
            setWaterVertex(15, p4, colorFadeNightBlue, waterU, waterV2);
            drawWaterColumn(batch, parentAlpha);
        }
    }

    private void drawToTargets(Batch batch, float parentAlpha) {
        batch.flush();
        metaBallTarget.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        for (WaterParticle particle : particles) {
            Vector2 position = particle.position;
            batch.draw(particleRegion, position.x - particleOriginX, position.y - particleOriginY, particleOriginX, particleOriginY, particleWidth, particleHeight, 1, 1, particle.orientation);
        }

        float scale = getScale();
        for (int i = 1; i < columns.size(); i++) {
            Vector2 p1 = new Vector2((i - 1) * scale, columns.get(i - 1).height);
            Vector2 p2 = new Vector2(i * scale, columns.get(i).height);
            Vector2 p3 = new Vector2(p1.x, p1.y - thickness);
            Vector2 p4 = new Vector2(p2.x, p2.y - thickness);

            setWaterVertex(0, p1, colorTransparent, waterU, waterV);
            setWaterVertex(5, p2, colorTransparent, waterU2, waterV);
            setWaterVertex(10, p4, colorWhite, waterU2, waterV2);
            setWaterVertex(15, p3, colorWhite, waterU, waterV2);
            drawWaterColumn(batch, parentAlpha);
        }
        batch.flush();
        metaBallTarget.end();

        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ZERO);
        particleTarget.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        batch.draw(metaBallTargetRegion, 0, 0);
        batch.flush();
        particleTarget.end();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void setWaterVertex(int offset, Vector2 position, float color, float u, float v) {
        setWaterVertex(offset, position.x, position.y, color, u, v);
    }

    private void setWaterVertex(int offset, float x, float y, float color, float u, float v) {
        waterVertices[offset] = x;
        waterVertices[offset + 1] = y;
        waterVertices[offset + 2] = color;
        waterVertices[offset + 3] = u;
        waterVertices[offset + 4] = v;
    }

    private void drawWaterColumn(Batch batch, float parentAlpha) {
        batch.draw(waterRegion.getTexture(), waterVertices, 0, 20);
    }
}
