package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/** Unit tests for Plane */
public class PlaneTests {

    private final double DELTA = 0.000001;


    /** Test method for {@link Plane#Plane(Point, Point, Point)}. */

    @Test
    void testConstructor() {
        Point p1 = new Point (2, 6, 2);
        Point p2 = new  Point (0, 0, 3);
        Point p3 = new Point (0, 0, 7);
        Point p4 = new Point(0, 0, 0);

        // =============== Boundary Values Tests ==================

        //TC11 test case for 2 of the same point

        String errorMessage = "ERROR: no error thrown for 2 similar points or wrong exception thrown";
        assertThrows(IllegalArgumentException.class, () -> new Plane(p3, p3, p1), errorMessage);

        assertThrows(IllegalArgumentException.class, () -> new Plane(p3, p1, p3), errorMessage);

        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p3, p3), errorMessage);

        //TC12 test case if all on the same line
        assertThrows(IllegalArgumentException.class, () -> new Plane(p4, p2, p3), "ERROR: no error thrown for all points on the same line or wrong exception thrown");
    }

    /** Test method for {@link geometries.Plane#getNormal(Point)}*/

    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============

        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);

        Plane p = new Plane(p1, p2, p3);

        // ensure there are no exceptions
        //assertDoesNotThrow(() -> p.getNormal(p1), "");

        // generate the test result
        Vector result = p.getNormal(p1);

        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Plane's normal is not a unit vector");

        // ensure the result is orthogonal to all the edges
        assertEquals(0, result.dotProduct(p1.subtract(p2)),
                "Normal is not orthogonal to the Plane");

        assertEquals(0, result.dotProduct(p1.subtract(p3)),
                "Normal is not orthogonal to the Plane");

        assertEquals(0, result.dotProduct(p2.subtract(p3)),
                "Normal is not orthogonal to the Plane");
    }


    /** Test method for {@link Plane#findIntersections(primitives.Ray)} */

    @Test
    public void testFindIntersections() {
        Plane plane = new Plane(new Point(0, 0, 1), new Vector(1, 1, 1));


        // ============ Equivalence Partitions Tests ==============

        //TC01 Ray intersects the plane
        assertEquals(List.of(new Point(1, 0, 0)),
                plane.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0))),
                "ERROR: Bad plane intersection");
        //TC02 Ray does not intersect plane
        assertNull(plane.findIntersections(new Ray(new Point(0,5,0), new Vector(1,0,0))), "ERROR: intersection when there isn't meant to be");


        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to Plane
        //TC11 Ray intersects Plane
        assertNull(plane.findIntersections(new Ray(new Point(0,0.5,0.5), new Vector(0,1,-1))), "ERROR: plane intersection when shouldn't be");

        //TC12 Ray does not intersect Plane
        assertNull(plane.findIntersections(new Ray(new Point(1,1,1), new Vector(0,1,-1))), "ERROR: plane intersection when shouldn't be");

        // **** Group: Ray is orthogonal to Plane
        //TC13 Ray intersects Plane
        assertEquals(List.of(new Point(1d/3, 1d/3, 1d/3)),
                plane.findIntersections(new Ray(new Point(1,1,1), new Vector(-1,-1,-1))), "ERROR: plane intersection is incorrect");

        //TC14 Ray does not intersect Plane
        assertNull(plane.findIntersections(new Ray(new Point(1,1,1), new Vector(1,1,1))), "ERROR: plane has intersect when shouldn't");

        //TC15 Ray starts in Plane
        assertNull(plane.findIntersections(new Ray(new Point(1d/3,1d/3,1d/3), new Vector(-1,-1,-1))), "ERROR: plane has intersect when shouldn't");

        // **** Group: Special cases
        //TC16 Ray is neither orthogonal nor parallel but starts in Plane
        assertNull(plane.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(0,1,0))), "ERROR: plane has intersect when shouldn't");

        //TC17 Ray is neither orthogonal nor parallel but starts from Plane's Q point
        assertNull(plane.findIntersections(new Ray(new Point(0,0,1), new Vector(0,1,0))), "ERROR: plane has intersect when shouldn't");
    }

    /** tests findGeoIntersections for max distance */
    @Test
    public void findGeoIntersections(){
        Plane plane = new Plane(new Point(0, 0, 1), new Vector(1, 1, 1));

        //TC intersect out of bounds
        assertNull(plane.findGeoIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(0,1,-1)), 0.1), "ERROR: plane has intersect when out of bounds");
    }
}