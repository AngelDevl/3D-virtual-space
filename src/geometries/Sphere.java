package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.List;

/**
 * Sphere class that's represent a sphere in a 3D Cartesian coordinate system
 */
public class Sphere extends RadialGeometry {

    /**
     * Sphere Constructor that initialize a new Sphere object with a center point and a radius
     * @param center the center of the sphere
     * @param radius the radius of the sphere
     */
    Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    @Override public List<Point> findIntersections(Ray ray) {
        return null;
    }

    @Override public Vector getNormal(Point outerPoint) {
        // To calculate the normal we need to subtract the outer point by the center point and then normalize
        return (outerPoint.subtract(center)).normalize();
    }

    /**
     * the center of the sphere
     */
    private final Point center;
}