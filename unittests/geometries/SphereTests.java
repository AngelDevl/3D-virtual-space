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



    /** Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}. */

    @Test
    public void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============

        Point center = new Point(-3, 0, 0);
        Sphere sphere = new Sphere(center, 1);

        //TC01: Ray is outside the sphere - no intersections
        assertNull(sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 0, 2))), "Ray is outside of the sphere");


        //TC02: Ray is starting behind the sphere and intersecting the sphere - two intersections
        List<Point> intersections = sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(-1, 0, 0)));
        List<Point> expectedIntersections = List.of(new Point(-2, 0, 0), new Point(-4, 0, 0));
        assertEquals(expectedIntersections, intersections, "Ray don't have two intersections with sphere");


        //TC03: Ray is starting inside the sphere and intersecting the sphere - one intersection
        List<Point> intersections1 = sphere.findIntersections(new Ray(new Point(-3.5, 0, 0), new Vector(1, 0, 0)));
        List<Point> expectedIntersections1 = List.of(new Point(-2, 0, 0));

        assertEquals(expectedIntersections1, intersections1, "Ray don't have one intersection with sphere when ray starting inside the sphere");

        //TC04: Ray starts after the sphere
        assertNull(sphere.findIntersections(new Ray(new Point(-4.5, 0, 0), new Vector(-1, 0, 0))), "Ray has intersections with sphere when the start point is after the sphere");

        // =============== Boundary Values Tests ==================

        //TC11: Ray starts at sphere and goes inside - one intersection
        List<Point> intersections2 = sphere.findIntersections(new Ray(new Point(-4, 0, 0), new Vector(1, 0, 1)));
        List<Point> expectedIntersections2 = List.of(new Point(-3, 0, 1));

        assertEquals(expectedIntersections2, intersections2, "Ray don't have two intersections with sphere");


        //TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-4, 0, 0), new Vector(-1, 0, -1))), "TC12: Ray from the sphere outwards.");

        // **** Group: Ray's line goes through the center
        //TC13: Ray starts before the sphere (2 points)
        assertEquals(List.of(new Point(-4, 0, 0), new Point(-2, 0, 0)), sphere.findIntersections(new Ray(new Point(-5, 0, 0), new Vector(1, 0, 0))), "TC13: Ray through center 2 points.");

        //TC14: Ray starts at sphere and goes inside (1 points)
        assertEquals(List.of(new Point(-2, 0, 0)), sphere.findIntersections(new Ray(new Point(-4, 0, 0), new Vector(1, 0, 0))), "TC14: Ray on sphere through center inwards.");

        //TC15: Ray starts inside after the center (1 points)
        assertEquals(List.of(new Point(-2, 0, 0)), sphere.findIntersections(new Ray(new Point(-2.5, 0, 0), new Vector(1, 0, 0))), "TC15: Ray in sphere through center");

        //TC16: Ray starts at the center (1 points)
        assertEquals(List.of(new Point(-2, 0, 0)), sphere.findIntersections(new Ray(new Point(-3, 0, 0), new Vector(1, 0, 0))), "TC16: Ray from center.");

        //TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-4, 0, 0), new Vector(-1, 0, 0))), "TC17: Ray on sphere through center outwards.");

        //TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0))), "TC18: Ray out of sphere through center.");


        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        //TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0, 0, 1), new Vector(-1, 0, 0))), "TC19: Ray tangent to the sphere.");

        //TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(-3, 0, 1), new Vector(-1, 0, 0))), "TC20: Ray tangent to the sphere");

        //TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(-4, 0, 1), new Vector(-1, 0, 0))), "TC21: Ray tangent to the sphere.");

        // **** Group: Special cases
        //TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(0, 0, 1))), "TC22: Ray outside orthogonal to sphere center line.");
    }
}
