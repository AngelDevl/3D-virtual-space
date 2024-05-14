package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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


    /** Test method for {@link Triangle#findIntersections(primitives.Ray)}. */

    @Test
    public void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        // ============ Equivalence Partitions Tests ==============
        //TC01 inside triangle
        assertEquals(List.of(new Point(1d/3,1d/3,1d/3)), triangle.findIntersections(new Ray(new Point(1,1,1), new Vector(-1,-1,-1))), "ERROR: bad intersect");
        //TC02 in between triangle rays outside triangle
        Ray ray =new Ray(new Point(0, 0, 2), new Vector(-1, -1, 0));
        //first check if it is in Plane
        assertEquals(List.of(new Point(-0.5, -0.5, 2)), plane.findIntersections(ray), "ERROR: wrong intersect with plane");
        assertNull(triangle.findIntersections(ray), "ERROR: intersect when shouldn't be");
        //TC03 on other side of edge outside triangle
        //first check if it is in Plane
        ray = new Ray(new Point(0, 0, -1), new Vector(1, 1, 0));
        assertEquals(List.of(new Point(1, 1, -1)), plane.findIntersections(ray),
                "ERROR: wrong intersect with plane");
        assertNull(triangle.findIntersections(ray), "Bad intersection");

        // =============== Boundary Values Tests ==================
        //TC11 intersects on edge
        ray = new Ray(new Point(-1, -1, 0), new Vector(1, 1, 0));
        //check plane first
        assertEquals(List.of(new Point(0.5, 0.5, 0)), plane.findIntersections(ray),
                "ERROR: Wrong intersection with plane");
        assertNull(triangle.findIntersections(ray), "ERROR: intersect where it shouldn't be");
        //TC12 intersect on vertex
        ray = new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0));
        //check plane first
        assertEquals(List.of(new Point(0, 1, 0)), plane.findIntersections(ray),
                "ERROR: Wrong intersection with plane");
        assertNull(triangle.findIntersections(ray), "ERROR: intersect where it shouldn't be");
        //TC13 intersect on edge continuation
        ray = new Ray(new Point(-2, 0, 0), new Vector(1, 1, 0));
        assertEquals(List.of(new Point(-0.5, 1.5, 0)), plane.findIntersections(ray),
                "ERROR: Wrong intersection with plane");
        assertNull(triangle.findIntersections(ray), "ERROR: intersect where it shouldn't be");
    }
}
