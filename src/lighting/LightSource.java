package lighting;

import primitives.Color;
import primitives.Vector;
import primitives.Point;

/**
 * LightSource interface - interface to represent two functions that all light type have
 */
public interface LightSource {

    /**
     * Calculate the intensity in a given point
     * @param p the given point for calculation
     * @return the color of the given point
     */
    public Color getIntensity(Point p);

    /**
     * get the vector of the light
     * @param p the point that we calculate the light vector from
     * @return the light vector
     */
    public Vector getL(Point p);

    /**
     * gets distance from point to light source
     * @param point point
     * @return distance from point to light
     */
    public double getDistance(Point point);
}
