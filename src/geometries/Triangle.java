package geometries;
import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Triangle class that's represent a two-dimensional triangle in a 3D Cartesian coordinate system
 */
public class Triangle extends Polygon {

    /**
     * Triangle Constructor to initialize a new Triangle object with 3 points
     * @param p1 represent first point
     * @param p2 represent second point
     * @param p3 represent third point
     */
    Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override public List<Point> findIntersections(Ray ray) {
        return null;
    }

}