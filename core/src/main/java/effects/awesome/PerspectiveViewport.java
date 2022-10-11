package effects.awesome;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PerspectiveViewport extends Viewport {
    private float unitsPerPixel = 1;
    private PerspectiveCamera camera;
    /** Creates a new viewport using a new {@link OrthographicCamera}. */
    public PerspectiveViewport () {
        this(new PerspectiveCamera());
    }

    public PerspectiveViewport (PerspectiveCamera camera) {
        setCamera(camera);
        this.camera=camera;
    }

    public void update (int screenWidth, int screenHeight, boolean centerCamera) {
        setScreenBounds(0, 0, screenWidth, screenHeight);
        setWorldSize(screenWidth * unitsPerPixel, screenHeight * unitsPerPixel);
        apply(centerCamera);
        camera.update(true);
    }

    public float getUnitsPerPixel () {
        return unitsPerPixel;
    }

    /** Sets the number of pixels for each world unit. Eg, a scale of 2.5 means there are 2.5 world units for every 1 screen pixel.
     * Default is 1. */
    public void setUnitsPerPixel (float unitsPerPixel) {
        this.unitsPerPixel = unitsPerPixel;
    }
}
