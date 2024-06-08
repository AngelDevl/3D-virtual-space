package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Sphere class that's represent a sphere in a 3D Cartesian coordinate system
 */
public class Sphere extends RadialGeometry {

    /**
     * Sphere Constructor that initialize a new Sphere object with a center point and a radius
     * @param center the center of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }


    @Override public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point rayHead = ray.head;
        Vector direction = ray.direction;

        // if the start point (head) of the ray is equals to the sphere center
        // than the only intersection would be on the surface of the sphere only
        if (rayHead.equals(center)) {
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        }

        // The point that between the two intersections points (if exists)
        // Achieved by connecting a new line from head to center,
        // and then find the Vector of the line by subtracting center with the center between the two intersection points.
        // After we calculated the vector we can simply do dot product between the direction vector and the one we find has explained above
        Vector fromCenter = center.subtract(rayHead);
        double t = alignZero(direction.dotProduct(fromCenter));

        // Here we use the formula of a circle X^2 + Y^2 = R^2 to find the distance between the center point and t (Point p)
        double y = Math.sqrt(alignZero(fromCenter.lengthSquared() - t * t));

        // if the y is equal or greater than the radius that means there are no intersections because the ray is outside the sphere
        if (y >= radius) return null;

        // To find x we use the radius and the y we found
        double x = alignZero(Math.sqrt(radius * radius - y * y));

        double t1 = alignZero(t - x);
        double t2 = alignZero(t + x);

        if (t1 <= 0 && t2 <= 0) return null;
        else if (t1 > 0 && t2 <= 0) return List.of(new GeoPoint(this, ray.getPoint(t1)));
        else if (t2 > 0 && t1 <= 0) return List.of(new GeoPoint(this, ray.getPoint(t2)));

        return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
    }


    @Override public Vector getNormal(Point outerPoint) {
        // To calculate the normal we need to subtract the outer point by the center point and then normalize
        return (outerPoint.subtract(center)).normalize();
    }

    /**
     * the center of the sphere
     */
    private final Point center;
}