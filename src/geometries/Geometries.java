package geometries;

import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class that's represent all geometries objects that Intersectable in a 3D Cartesian coordinate system
 */
public class Geometries extends Intersectable {

    private final List<Intersectable> geometries = new LinkedList<>();


    /**
     * Geometries Constructor to initialize the geoObjects - using add to add all the Intersectable objects
     * @param geometries all the geometries objects that implements Intersectable interface
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Default constructor for Geometries
     * Initialize geometries list to a new empty list
     */
    public Geometries() {

    }

    /**
     * add function adds all the shapes that given to a List in the class
     * @param geometries all the geometries objects that implements Intersectable interface
     */
    public void add(Intersectable... geometries) {
            this.geometries.addAll(List.of(geometries));
    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (Intersectable geometry : geometries) {
            List<GeoPoint> currentIntersections = geometry.findGeoIntersections(ray, maxDistance);
            if (currentIntersections == null) {
                continue;
            }

            // Allocate memory if not initialized yet
            if (intersections == null)
                intersections = new LinkedList<>();

            // Merge the lists
            intersections.addAll(currentIntersections);
        }

        return intersections;
    }
}