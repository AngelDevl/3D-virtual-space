package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;


public class CylinderTests {

    /** Test method for {@link geometries.Cylinder#getNormal(Point)} */

    @Test
    void testGetNormal() {

        Point p1 = new Point(1,2,0);
        Vector dir = new Vector(0,1,0);
        Ray axis = new Ray((new Point(0,1,0)), dir);
        Cylinder cylinder = new Cylinder(axis, 1, 10);

        // ============ Equivalence Partitions Tests ==============
        //TC01 normal on the round bit
        Vector n1 = new Vector(1,0,0);
        assertEquals(n1, cylinder.getNormal(p1), "ERROR: Round normal not working");
        //TC02 normal on the top
        Vector result = cylinder.getNormal(new Point(0.1,11,0));
        assertEquals(dir, result, "ERROR: normal on top is wrong");
        //TC03 normal on bottom
        result = cylinder.getNormal(new Point(0.1,1,0));
        assertEquals(dir, result, "ERROR: normal on bottom is wrong");
        // =============== Boundary Values Tests ==================
        //TC11 center of top base
        result = cylinder.getNormal(new Point(0,11,0));
        assertEquals(dir, result, "ERROR: normal on top center is wrong");
        //TC12 center on bottom base
        result = cylinder.getNormal(new Point(0,1,0));
        assertEquals(dir, result, "ERROR: normal on bottom center is wrong");
    }
}
