package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.List;

import static primitives.Util.isZero;
import static primitives.Util.alignZero;

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
        Point head = axis.head;
        Vector direction = axis.direction;
        double t = 0;

        // check tube BVA P-p0
        try {
            t = alignZero(p.subtract(head).dotProduct(direction));
        }
        catch (IllegalArgumentException e) {
            return direction;
        }

        // check if the point is at the base
        if(isZero(t) || isZero(height - t)) {
            return direction;
        }

        return p.subtract(axis.head.add(direction.scale(t))).normalize();
    }

    /**
     * The height of the cylinder
     */
    private final double height;
}
