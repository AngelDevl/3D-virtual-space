package geometries;

import primitives.Ray;
import primitives.Point;

import java.util.List;

/**
 * Intersectable interface that's represent an intersections between two geometries objects in a 3D Cartesian coordinate system
 */
public interface Intersectable {

    /**
     * findIntersections Function finds the intersection points between a ray and other geometries objects
     * @param ray the ray object that intersect with other object
     * @return a list of points that represent the intersections points
     */
    List<Point> findIntersections(Ray ray);
}