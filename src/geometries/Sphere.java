package geometries;
import primitives.Vector;
import primitives.Point;

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

    @Override public Vector getNormal(Point p) {
        return null;
    }

    /**
     * the center of the sphere
     */
    private final Point center;
}
