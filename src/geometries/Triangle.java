package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // First we will check if there are any intersection with the plane
        List<GeoPoint> planeIntersections = plane.findGeoIntersections(ray, maxDistance);

        // If there are no intersection with plane there are no with triangle
        if (planeIntersections == null)
            return null;

        // Then we will check if the point of intersection is inside a triangle
        Point head = ray.head;
        Vector direction = ray.direction;

        Vector v1 = vertices.get(0).subtract(head);
        Vector v2 = vertices.get(1).subtract(head);
        Vector v3 = vertices.get(2).subtract(head);

        // cross products
        double a1 = alignZero(direction.dotProduct(v1.crossProduct(v2)));
        double a2 = alignZero(direction.dotProduct(v2.crossProduct(v3)));
        double a3 = alignZero(direction.dotProduct(v3.crossProduct(v1)));

        if (isZero(a1) || isZero(a2) || isZero(a3)){
            return null;
        }

        if ((a1 > 0 && a2 > 0 && a3 > 0) || (a1 < 0 && a2 < 0 && a3 < 0)){
            return List.of(new GeoPoint(this, planeIntersections.getFirst().point));
        }

        return null;
    }
}