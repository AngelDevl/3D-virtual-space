package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * DirectionalLight class - light by direction
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * Constructor to initialize the direction vector and the color (intensity)
     * @param color intensity
     * @param direction the direction vector
     */
    public DirectionalLight(Color color, Vector direction) {
        super(color);
        this.direction = direction;
    }


    @Override public Color getIntensity(Point p) {
        // in directional light it's equal everywhere - IL = I0
        return getIntensity();
    }

    @Override public Vector getL(Point p) {
        return direction.normalize();
    }


    /** represent the direction of the light */
    private Vector direction;
}
