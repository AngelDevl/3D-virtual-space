package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

/** Unit tests for Sphere */
public class SphereTests {

    /** Test method for {@link geometries.Sphere#getNormal(Point)} */

    @Test
    void getNormal() {

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

}
