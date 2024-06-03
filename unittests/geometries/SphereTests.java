package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

/** Unit tests for Sphere */
public class SphereTests {

    /** Test method for {@link geometries.Sphere#getNormal(Point)} */

    @Test
    void testGetNormal() {

        // Assuming a sphere centered at (1,1,1) with radius 1
        Sphere sphere = new Sphere(new Point(1, 1, 1), 1);

        // ============ Equivalence Partitions Tests ==============

        //TC01: testCalculateNormal classic
        Point newPoint = new Point(1, 2, 1);
        assertEquals(new Vector(0, 1 ,0),
                sphere.getNormal(newPoint),
                "Failed test calculate normal classic sphere");

        //TC02: test getNormal sphere returns normalized vector
        assertEquals(1.0,(sphere.getNormal(newPoint)).length(),
                "Failed test getNormal sphere returns normalized vector");
    }


    /** Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)} */

    @Test
    public void testFindIntersections() {
        Point centerPoint = new Point(1, 0, 0);
        double sphereRadius = 1d;

        Sphere sphere = new Sphere(centerPoint, sphereRadius);

        Point intersection1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point intersection2 = new Point(1.53484692283495, 0.844948974278318, 0);

        var expectedIntersections = List.of(intersection1, intersection2);


        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));

        assertEquals(expectedIntersections, result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point(0.5, 0.5, 0), new Vector(3, 1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");

        // TC04: Ray starts after the sphere
        assertNull(sphere.findIntersections(new Ray(new Point(2,1,0), new Vector(3,1,0))), "Ray Crosses Sphere but isn't meant to");


        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        assertEquals(List.of(new Point(1,-1,0)),
                sphere.findIntersections(new Ray(new Point(0,0,0), new Vector(1,-1,0))), "ERROR: Ray from sphere inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,0), new Vector(-1,-1,0))), "ERROR: Ray from sphere outside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point(-1,0,0), new Vector(1,0,0)));
        assertEquals(2, result.size(), "ERROR: through center wrong number of points");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }

        assertEquals(List.of(new Point(0,0,0), new Point(2,0,0)), result, "ERROR: wrong points through center");

        // TC14: Ray starts at sphere and goes inside (1 points)
        assertEquals(List.of(new Point(2,0,0)),
                sphere.findIntersections(new Ray(new Point(0,0,0), new Vector(1,0,0))), "ERROR: wrong point when start on sphere to center");

        // TC15: Ray starts inside (1 points)
        assertEquals(List.of(new Point(2,0,0)),
                sphere.findIntersections(new Ray(new Point(1.5,0,0), new Vector(1,0,0))), "ERROR: wrong point when start inside sphere to center");

        // TC16: Ray starts at the center (1 points)
        assertEquals(List.of(new Point(2,0,0)),
                sphere.findIntersections(new Ray(new Point(1,0,0), new Vector(1,0,0))), "ERROR: wrong point when start at center");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,0), new Vector(-1,0,0))), "ERROR: wrong point when start on sphere to outside");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1,0,0), new Vector(-1,0,0))), "ERROR: wrong point when start after sphere to outside center");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,-1,0), new Vector(0,1,0))), "ERROR: wrong point when tangent before sphere");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,0), new Vector(0,1,0))), "ERROR: wrong point when tangent starts on sphere");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,1,0), new Vector(0,1,0))), "ERROR: wrong point when tangent after sphere");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(-1,0,0), new Vector(0,1,0))), "ERROR: wrong point when orthogonal to center");
    }
}