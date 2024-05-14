package geometries;
import primitives.Vector;
import primitives.Point;

/**
 * Geometry interface for all geometry related
 */
public interface Geometry extends Intersectable {

    /**
     * getNormal function that calculate the normal with different geometrical shapes (implemented for classes the implements the interface)
     * @param p point to calculate the normal with
     * @return The normal vector
     */
    public Vector getNormal(Point p);
}