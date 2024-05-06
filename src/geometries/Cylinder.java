package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

/**
 * Cylinder class that's represent a cylinder in a 3D Cartesian coordinate system
 */
public class Cylinder extends Tube {

    /**
     * Cylinder Constructor to initialize a new Cylinder object with axis, height and radius
     * @param axis the main axis that's tells the direction of the tube
     * @param radius the radius of the cylinder
     * @param height the height of the cylinder
     */
    Cylinder(Ray axis, double radius, double height) {
        super(axis, radius);

        this.height = height;
    }

    @Override public Vector getNormal(Point p) {
        return null;
    }

    /**
     * The height of the cylinder
     */
    private final double height;
}
