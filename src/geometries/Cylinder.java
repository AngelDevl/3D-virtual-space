package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

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
        Point point = axis.head;
        Vector vec = axis.direction;
        double t = 0;

        // check tube BVA P-p0
        try {
            t = alignZero(p.subtract(point).dotProduct(vec));
        }
        catch (IllegalArgumentException e) {
            return vec;
        }

        // check if the point is at the base
        if(isZero(t) || isZero(height - t)){
            return vec;
        }

        // reassign p0 so don't need to create new obj
        point = point.add(vec.scale(t));
        return p.subtract(point).normalize();
    }

    /**
     * The height of the cylinder
     */
    private final double height;
}
