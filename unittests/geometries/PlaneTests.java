package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

/** Unit tests for Plane */
public class PlaneTests {

    private final double DELTA = 0.000001;

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
}
