package effects.awesome.lightning;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class BranchLightningBolt extends SimpleLightningBolt {
    public BranchLightningBolt(Vector2 source, Vector2 dest, Color color, Texture halfCircle, Texture lineSegment) {
        super(source, dest, color, halfCircle, lineSegment);
    }


}
