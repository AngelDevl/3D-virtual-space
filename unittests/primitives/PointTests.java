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
        Point expectedResult = new Point(2, 4, 6);

        assertEquals(p1.add(v1), expectedResult, "Addition between point and vector failed");


        //TC02: Test that addition of different Vectors returns different results

        Vector v2 = new Vector(2, 4, 6);
        assertNotEquals(p1.add(v1), p1.add(v2), "Adding different vectors to a point return the same result");


        //TC03: Test adding Vector with negative values

        Vector v3 = new Vector(-4, -5, -6);
        Point expectedResult2 = new Point(-3, -3, -3);
        assertEquals(expectedResult2, p1.add(v3),"Addition of Vector with negative numbers failed");


        // =============== Boundary Values Tests ==================


        //TC10: Addition of Vector with negative coordinates to the Point

        Vector negativeVector = new Vector(-1, -2, -3);
        Point zeroPoint = new Point(0, 0, 0);
        assertEquals(p1.add(negativeVector), zeroPoint,"Addition with Vector with negative coordinates to the Point failed");

        //TC11: Addition of zero Point

        Point expectedResult3 = new Point(1, 2, 3);
        assertEquals(expectedResult3, zeroPoint.add(v1),"Addition of zero Point failed");
    }


    /** Test method for {@link primitives.Point#subtract(Point)} */

    @Test
    public void testSubtract() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: Simple subtraction of Point

        Point p1 = new Point(1,2,3);
        Point p2 =  new Point(4,5,6);
        Vector expectedResult1 = new Vector(3,3,3);
        assertEquals(expectedResult1, p2.subtract(p1),"Subtraction doesn't work");

        //TC02: Test that Subtraction of different Points from the same Point returns different results

        Point p3 = new Point(1, 1, 1);
        assertNotEquals(p2.subtract(p1), p2.subtract(p3), "Subtracting 2 different Points from the same Point returns same result");


        //TC03: Test subtracting same Point from different Points returns different results

        assertNotEquals(p2.subtract(p1), p3.subtract(p1), "subtracting same Point from different Points returns same results");


        //TC04: Test subtracting Point with negative values

        Point p4 = new Point(-4, -5, -6);
        Vector expectedResult2=new Vector(5, 7, 9);
        assertEquals(expectedResult2, p1.subtract(p4), "Subtraction of Point with negative values doesn't work");


        // =============== Boundary Values Tests ==================


        //TC10: Subtraction of zero Point

        Point zeroPoint = new Point(0, 0, 0);
        Vector expectedResult = new Vector(1,2,3);
        assertEquals(expectedResult, p1.subtract(zeroPoint), "Subtraction of zero Point doesn't return Vector with same coordinates");


        //TC11: Subtraction from zero Point

        Vector expectedResult3 = new Vector(-1, -2, -3);
        assertEquals(expectedResult3, zeroPoint.subtract(p1), "Subtraction from zero Point doesn't work");
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

        assertEquals(p1.distanceSquared(p3),p3.distanceSquared(p1),DELTA,
                "Distance squared between 2 Points isn't the same both ways");


        //TC04: Test to see if the distance squared works with negative numbers

        Point p4 = new Point(-1, -2, -3);
        assertEquals(56,p1.distanceSquared(p4),DELTA, "Distance squared doesn't work with negative numbers");


        // =============== Boundary Values Tests ==================


        //TC10: Test to check the distance squared from the origin Point (0,0,0)

        Point p5 = new Point(0, 0, 0);
        assertEquals(p1.distanceSquared(p5), 14, DELTA, "Distance from origin doesn't work");
    }

    /** Test method for {@link primitives.Point#distance(Point)} */

    @Test
    void testDistance() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: Test for if distance of Point from itself is 0

        Point p1 = new Point(1, 4, 9);
        assertEquals(0, p1.distance(p1),DELTA, "Distance of Point from itself is not 0");

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
