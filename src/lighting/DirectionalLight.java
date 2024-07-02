package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * DirectionalLight class - light by direction
 */
public class DirectionalLight extends Light implements LightSource {

    /** represent the direction of the light */
    private Vector direction;

    /**
     * Constructor to initialize the direction vector and the color (intensity)
     * @param intensity new intensity
     * @param direction the direction vector
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }


    @Override
    public Color getIntensity(Point p) {
        // in directional light it's equal everywhere - IL = I0
        return getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }

    @Override
    public double getDistance(Point p) { return Double.POSITIVE_INFINITY; }
}
