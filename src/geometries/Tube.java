package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {

    Tube(Ray axis, double radius) {
        super(radius);

        this.axis = axis;
    }

    @Override public Vector getNormal(Point p) {
        return null;
    }

    protected final Ray axis;
}
