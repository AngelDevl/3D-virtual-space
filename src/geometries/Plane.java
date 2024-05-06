package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * Plane class that's represent a two-dimensional plane in a 3D Cartesian coordinate system
 */
public class Plane implements Geometry {

    /**
     * Plane Constructor that initialize a new Plane object and calculates the normal using two points (not implemented yet),
     * and saving one point that lies on the plane. The constructor normalize the new normal vector after calculation
     * @param p point that lies on the plane
     * @param norm1 the first point that used to calculate the normal in a plane
     * @param norm2 the second point that used to calculate the normal in a plane
     */
    Plane(Point p, Point norm1, Point norm2) {
        normal = null;
        this.p = p;
    }

    /**
     * Plane Constructor that initialize a new Plane object with a normal and a point that lies on the plane.
     * The constructor normalize the normal vector in case that the passed vector is not normalize
     * @param p point that lies on the plane
     * @param normal the normal vector of the plane
     */
    Plane(Point p, Vector normal) {
        this.normal = normal.normalize();
        this.p = p;
    }

    @Override public Vector getNormal(Point p) {
        return null;
    }

    /**
     * Getter function that return the normal of the plane
     * @return the normal of the current plane
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * The normal vector
     */
    private final Vector normal;

    /**
     * a point that lies on the plane
     */
    private final Point p;
}
