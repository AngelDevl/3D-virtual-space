package geometries;
import primitives.Vector;
import primitives.Point;

public class Sphere extends RadialGeometry {

    Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    public Vector getNormal(Point p) {
        return null;
    }

    private final Point center;
}
