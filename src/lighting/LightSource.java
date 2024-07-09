package lighting;

import primitives.Color;
import primitives.Vector;
import primitives.Point;

import java.util.List;

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
     * get the direction vector of the light from a point
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

    /**
     * Get a beam of rays from a point on a geometry towards a light,
     * all the rays are constructed within the soft shadow radius boundary
     *
     * @param p point on the geometry
     * @return List of rays from the geometry to the soft shadow radius
     */
    public List<Vector> getListL(Point p);
}
