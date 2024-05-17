package primitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
}