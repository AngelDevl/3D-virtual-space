package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

/** Unit tests for Triangle */
public class TriangleTests {

    private final double DELTA = 0.000001;

    /** Test method for {@link geometries.Triangle#getNormal(Point)} */

    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here - using a quad
        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);

        Triangle pol = new Triangle(p1, p2, p3);

        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(p1), "");

        // generate the test result
        Vector result = pol.getNormal(p1);

        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA,
                "Triangle's normal is not a unit vector");

        // ensure the result is orthogonal to all the edges
        assertEquals(0, result.dotProduct(p1.subtract(p2)),
                "Normal is not orthogonal to the triangle");

        assertEquals(0, result.dotProduct(p1.subtract(p3)),
                "Normal is not orthogonal to the triangle");

        assertEquals(0, result.dotProduct(p2.subtract(p3)),
                "Normal is not orthogonal to the triangle");
    }

}
