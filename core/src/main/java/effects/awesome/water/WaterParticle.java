package effects.awesome.water;

import com.badlogic.gdx.math.Vector2;

public class WaterParticle {
    public Vector2 position;
    public Vector2 velocity;
    public float orientation;

    public WaterParticle(Vector2 position, Vector2 velocity, float orientation) {
        this.position=position;
        this.velocity=velocity;
        this.orientation=orientation;
    }
}
