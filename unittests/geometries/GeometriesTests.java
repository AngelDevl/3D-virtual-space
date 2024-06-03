package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class GeometriesTests {

    /** Test method for {@link geometries.Geometries#add(Intersectable...)} */

    @Test
    public void testFindIntersections() {
        Ray ray = new Ray(new Point(0,1.5,0.5), new Vector(0,1,0));
        Plane plane1 = new Plane(new Point(0,1,0), new Vector(0,1,0));
        Triangle triangle1 = new Triangle(new Point(0,2,0), new Point(-2, 2, 4), new Point(2,2,4));
        Sphere sphere1 = new Sphere(new Point(0,4,0), 1);
        Geometries geometries = new Geometries(plane1, triangle1, sphere1);

        // ============ Equivalence Partitions Tests ==============

        //TC01 goes through some but not all
        assertEquals(3, geometries.findIntersections(ray).size(), "ERROR: wrong amount of points");
        // =============== Boundary Values Tests ==================
        ray = new Ray(new Point(0,-3,0), new Vector(0,0,1));

        //TC11 empty collection
        Geometries emptyCollection = new Geometries();
        assertNull(emptyCollection.findIntersections(ray), "ERROR: intersect when shouldn't be");

        //TC12 none get cut
        assertNull(geometries.findIntersections(ray), "ERROR: intersect when shouldn't be");

        //TC13 one is cut
        ray = new Ray(new Point(2,4,0), new Vector(-1,0,0));
        assertEquals(2, geometries.findIntersections(ray).size(), "ERROR: wrong amount of points");

        //TC14 all are cut
        ray = new Ray(new Point(0,-1,0.5), new Vector(0,1,0));
        assertEquals(4, geometries.findIntersections(ray).size(), "ERROR: wrong amount of points");
    }
}