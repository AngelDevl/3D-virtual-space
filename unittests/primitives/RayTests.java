package primitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/** Unit tests for Ray */
class RayTests {

     /** Test method for {@link Ray#getPoint(double)}} */

    @Test
    public void testGetPoint() {
        Ray ray = new Ray(new Point(1,0,0), new Vector(-1,0,0));

        // ============ Equivalence Partitions Tests ==============

        //TC01 Positive
        assertEquals(new Point(-1,0,0), ray.getPoint(2), "ERROR: getPoint positive doesn't work");

        //TC02 Negative
        assertEquals(new Point(3,0,0), ray.getPoint(-2), "ERROR: getPoint negative doesn't work");

        // =============== Boundary Values Tests ==================

        //TC11 zero
        assertEquals(ray.head, ray.getPoint(0), "ERROR: getPoint zero doesn't work");
    }


    /** Test method for {@link Ray#findClosestPoint(List)}} */

    @Test
    public void testFindClosestPoint(){
        Ray ray = new Ray(new Point(1,0,0), new Vector(1,0,0));
        Point point = new Point(2,0,0);

        // ============ Equivalence Partitions Tests ==============

        //TC01 point in middle of list
        List<Point> list = new LinkedList<Point>();
        Collections.addAll(list, new Point(5,0,0), point, new Point(-7,0,0));
        assertEquals(point, ray.findClosestPoint(list), "ERROR: wrong point for middle of list");

        // =============== Boundary Values Tests ==================

        //TC11 empty list
        assertNull(ray.findClosestPoint(null), "ERROR: should return null for empty point");

        //TC12 point at beginning of list
        list = new LinkedList<>();
        Collections.addAll(list, point, new Point(5,0,0), new Point(-7,0,0));
        assertEquals(point, ray.findClosestPoint(list), "ERROR: wrong point for front of list");

        //TC13 point at end of list
        list.addLast(point);
        list.removeFirst();
        assertEquals(point, ray.findClosestPoint(list), "ERROR: wrong point for back of list");
    }
}