package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


/**
 * Tube class that's represent a tube in a 3D Cartesian coordinate system
 */
public class Tube extends RadialGeometry {

    /**
     * Tube Constructor to initialize a new Tube object with radius and axis
     * @param axis a Ray object that's represent the main axis
     * @param radius the radius of the tube
     */
    Tube(Ray axis, double radius) {
        super(radius);

        this.axis = axis;
    }

    @Override public Vector getNormal(Point p) {
        return null;
    }

    /**
     * the main axis that's tells the direction of the tube
     */
    protected final Ray axis;
}
