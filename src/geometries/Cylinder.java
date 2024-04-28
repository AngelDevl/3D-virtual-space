package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

public class Cylinder extends Tube {

    Cylinder(Ray ray, double radius, double height) {
        super(ray, radius);

        this.height = height;
    }

    @Override public Vector getNormal(Point p) {
        return null;
    }

    private final double height;
}
