package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;


public class CylinderTests {

    /** Test method for {@link geometries.Cylinder#getNormal(Point)} */
    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: Point on the curved surface of the cylinder
        Vector direction = new Vector(0, 1, 0);
        Point head = new Point(0, 0, 0);
        Ray axis = new Ray(head, direction);
        Cylinder cylinder = new Cylinder(axis, 1, 2);
        Point pointOnCurvedSurface = new Point(1, 1, 0);
        Vector expectedNormalOnCurvedSurface = new Vector(1, 0, 0);
        assertEquals(expectedNormalOnCurvedSurface, cylinder.getNormal(pointOnCurvedSurface),
                "Failed to get normal on the curved surface of the cylinder");

        //TC02: Point on one of the end caps of the cylinder
        Point pointOnEndCap = new Point(0, 0, 0); // On the bottom end cap
        Vector expectedNormalOnEndCap = new Vector(0, -1, 0);
        assertEquals(expectedNormalOnEndCap, cylinder.getNormal(pointOnEndCap),
                "Failed to get normal on the end cap of the cylinder");

        // =============== Boundary Values Tests ==================

        //TC10: Point on the side of the cylinder orthogonal to the axis
        Point pointOnSide = new Point(1, 0, 1);
        Vector expectedNormalOnSide = new Vector(1, 0, 0);
        assertEquals(expectedNormalOnSide, cylinder.getNormal(pointOnSide),
                "Failed to get normal on the side of the cylinder");

    }
}
