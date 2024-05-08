package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

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
        // dot product between the direction vector and the given point subtracted by the head point (start point)
        double t = axis.direction.dotProduct(p.subtract(axis.head));

        if (isZero(t)) // orthogonal - if dot product of two vectors is 0 the vectors are orthogonal
            return ((p.subtract(axis.head)).normalize()); // subtract the given point by the starting point (head) then normalize

        // Center point of the tube - by adding to starting point the direction vector scaled by the result of the dot product above
        Point o = axis.head.add(axis.direction.scale(t));
        return (p.subtract(o)).normalize();
    }

    /**
     * the main axis that's tells the direction of the tube
     */
    protected final Ray axis;
}
