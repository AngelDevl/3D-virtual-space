package geometries;
import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {

    Plane(Point p1, Point p2, Point p3) {
        normal = null;
        p = p1;
    }

    Plane(Point p, Vector normal) {
        this.normal = normal.normalize();
        this.p = p;
    }

    public Vector getNormal(Point p) {
        return null;
    }

    public Vector getNormal() {
        return normal;
    }

    private final Vector normal;
    private final Point p;
}
