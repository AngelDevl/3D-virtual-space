package primitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Unit tests for Point */
public class PointTests {

    /** Delta value for accuracy when comparing the numbers of type 'double' in assertEquals */

    private final double DELTA = 0.000001;

    /** Test method for {@link primitives.Point#add(Vector)} */

    @Test
    public void testAdd() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: Simple addition of Vector
        Point p1 = new Point(1, 2, 3);
        Vector v1 = new Vector(1, 2, 3);

        assertEquals(new Point(2, 4, 6), p1.add(v1), "Addition between point and vector failed");


        //TC02: Test that addition of different Vectors returns different results

        assertNotEquals(p1.add(v1), p1.add(new Vector(2, 4, 6)), "Adding different vectors to a point return the same result");


        //TC03: Test adding Vector with negative values

        assertEquals(new Point(-3, -3, -3), p1.add(new Vector(-4, -5, -6)),"Addition of Vector with negative numbers failed");


        // =============== Boundary Values Tests ==================


        //TC10: Addition of Vector with negative coordinates to the Point

        Vector negativeVector = new Vector(-1, -2, -3);
        Point zeroPoint = new Point(0, 0, 0);
        assertEquals(zeroPoint, p1.add(negativeVector),"Addition with Vector with negative coordinates to the Point failed");

        //TC11: Addition of zero Point

        assertEquals(new Point(1, 2, 3), zeroPoint.add(v1),"Addition of zero Point failed");
    }


    /** Test method for {@link primitives.Point#subtract(Point)} */

    @Test
    public void testSubtract() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: Simple subtraction of Point

        Point p1 = new Point(1,2,3);
        Point p2 =  new Point(4,5,6);
        assertEquals(new Vector(3,3,3), p2.subtract(p1),"Subtraction doesn't work");

        //TC02: Test that Subtraction of different Points from the same Point returns different results

        Point p3 = new Point(1, 1, 1);
        assertNotEquals(p2.subtract(p1), p2.subtract(p3), "Subtracting 2 different Points from the same Point returns same result");


        //TC03: Test subtracting same Point from different Points returns different results

        assertNotEquals(p2.subtract(p1), p3.subtract(p1), "subtracting same Point from different Points returns same results");


        //TC04: Test subtracting Point with negative values

        Point p4 = new Point(-4, -5, -6);
        assertEquals(new Vector(5, 7, 9), p1.subtract(p4), "Subtraction of Point with negative values doesn't work");


        // =============== Boundary Values Tests ==================


        //TC10: Subtraction of zero Point

        Point zeroPoint = new Point(0, 0, 0);
        assertEquals(new Vector(1,2,3), p1.subtract(zeroPoint), "Subtraction of zero Point doesn't return Vector with same coordinates");


        //TC11: Subtraction from zero Point

        assertEquals(new Vector(-1, -2, -3), zeroPoint.subtract(p1), "Subtraction from zero Point doesn't work");

        //TC12: Zero vector returned from subtract

        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "Constructor (Double3 (which we use in Vector.subtract)) didn't throw an IllegalArgumentException");
    }

    /** Test method for {@link primitives.Point#distanceSquared(Point)} */

    @Test
    void testDistanceSquared() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: Test for if distance squared of Point from itself is 0

        Point p1 = new Point(1, 2, 3);
        assertEquals(0, p1.distanceSquared(p1), DELTA, "Distance squared of Point from itself is not 0");

        //TC02: Test if distance squared between 2 random Points is as expected

        Point p3 = new Point(1, 2, 1);
        assertEquals(4, p1.distanceSquared(p3), DELTA, "Distance squared between 2 Points is not as expected");


        //TC03: Test if the distance squared between 2 Points is the same both ways

        assertEquals(p1.distanceSquared(p3), p3.distanceSquared(p1), DELTA,
                "Distance squared between 2 Points isn't the same both ways");


        //TC04: Test to see if the distance squared works with negative numbers

        Point p4 = new Point(-1, -2, -3);
        assertEquals(56, p1.distanceSquared(p4), DELTA, "Distance squared doesn't work with negative numbers");


        // =============== Boundary Values Tests ==================


        //TC10: Test to check the distance squared from the origin Point (0,0,0)

        Point p5 = new Point(0, 0, 0);
        assertEquals(14, p1.distanceSquared(p5), DELTA, "Distance from origin doesn't work");
    }

    /** Test method for {@link primitives.Point#distance(Point)} */

    @Test
    void testDistance() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: Test for if distance of Point from itself is 0

        Point p1 = new Point(1, 4, 9);
        assertEquals(0, p1.distance(p1), DELTA, "Distance of Point from itself is not 0");

        //TC02: Test if distance squared between 2 random Points is as expected

        Point p3 = new Point(1, 4, 1);
        assertEquals(8, p1.distance(p3), DELTA, "Distance between 2 Points is not as expected");

        //TC03: Test if the distance between 2 Points is the same both ways

        assertEquals(p1.distance(p3), p3.distance(p1), DELTA,
                "Distance between 2 Points isn't the same both ways");

        //TC04: Test to see if the distance works with negative numbers

        Point p4 = new Point(1, 4, -1);
        assertEquals(10, p1.distance(p4), DELTA, "Distance doesn't work with negative numbers");


        // =============== Boundary Values Tests ==================


        //TC10: Test to check the distance squared from the origin Point (0,0,0)

        Point p5 = new Point(0, 0, 0);
        Point p6 = new Point(4, 3, 0);
        assertEquals(5, p6.distance(p5), DELTA, "Distance from origin doesn't work");
    }
}
