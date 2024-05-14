package primitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Unit tests for Vector */
public class VectorTests {

    /** Test method for {@link primitives.Vector#Vector(double, double, double)} */

    @Test
    void TestConstructor() {

        //============ Equivalence Partitions Tests ==============


        //============ Boundary Partitions Tests ==============

        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "Constructor (x, y, z) didn't throw an IllegalArgumentException");
    }

    /** Test method for {@link primitives.Vector#Vector(Double3)} */

    @Test
    void TestConstructorDouble3() {

        //============ Equivalence Partitions Tests ==============


        //============ Boundary Partitions Tests ==============

        assertThrows(IllegalArgumentException.class, () -> new Vector(Double3.ZERO), "Constructor (Double3) didn't throw an IllegalArgumentException");
    }


    /** Test case for {@link primitives.Vector#add(Vector)} */

    @Test
    void add() {

        // ============ Boundary Partitions Tests ==============

        //TC01: test for addition with self (same object)
        Vector vector0 = new Vector(1.0, 2.0, 3.0);
        Vector expectedSolution = new Vector(2, 4, 6);
        assertEquals(expectedSolution, vector0.add(vector0),
                "Addition with the same object(vector) failed");

        //TC02: test for addition with the opposite vector (vector0 + vector1 = vector (0, 0, 0))
        Vector vector1 = new Vector(-1, -2, -3);
        assertThrows(IllegalArgumentException.class, () -> vector1.add(vector0), "Vector equals to Zero Vector (0, 0, 0)");


        // ============ Equivalence Partitions Tests ==============

        //TC10: check add vector inequality

        Vector vectorA = new Vector(2.0, 2.0, 2.0);
        Vector vectorB = new Vector(1.0, 1.0, 2.0);
        assertNotEquals(vectorA.add(vectorB), vectorA, "Vector inequality failed");

        //TC11: test Vector Equality

        Vector vectorD = new Vector(2.0, 4.0, 6.0);
        assertEquals(vector0.add(vector0), vectorD, "Failed equality addition");

        //TC12: Test if Vector addition goes both ways
        assertEquals(vectorA.add(vectorB), vectorB.add(vectorA), "Vector addition doesn't go both ways");
    }

    /** Test case for {@link primitives.Vector#scale(double)} */

    @Test
    void scale() {

        //============ Equivalence Partitions Tests ==============


        //TC01: test Scale By Positive Scalar

        Vector vector0 = new Vector(1.0, 2.0, 3.0);
        assertEquals(new Vector(2.0, 4.0, 6.0), vector0.scale(2.0),
                "Failed scale with positive scalar");


        //TC02: multiplication by negative
        assertEquals(new Vector(-1, -2, -3), vector0.scale(-1),
                "Failed negative scale");


        // ============ Boundary Partitions Tests ==============


        //TC10: test Scale by 1

        assertEquals(vector0.scale(1), vector0, "Vector scale by 1 failed");


        //TC11: test scale by 0

        assertThrows(IllegalArgumentException.class, () -> vector0.scale(0), "Vector equals to Zero Vector (0, 0, 0)");


    }

    /** Test case for {@link primitives.Vector#dotProduct(Vector)} */


    @Test
    void dotProduct() {

        //============ Equivalence Partitions Tests ==============

        //TC01: testDotProductStandard

        Vector vectorA = new Vector(1.0, 2.0, 3.0);
        Vector vectorB = new Vector(4.0, 5.0, 6.0);
        assertEquals(32.0, vectorA.dotProduct(vectorB), 1e-10,
                "Failed dot product standard (positive numbers) test");


        //TC02: test dot product with negative component

        Vector vectorNegative = new Vector(-1.0, -2.0, -3.0);
        Vector vectorPlus = new Vector(4.0, 5.0, 6.0);
        assertEquals(-32.0, vectorPlus.dotProduct(vectorNegative), 1e-10,
                "Failed negative and positive dot product test");


        //============ Boundary Partitions Tests ==============


        //TC10: test dot product with orthogonal Vectors

        Vector vectorX = new Vector(1.0, 0.0, 0.0);
        Vector vectorY = new Vector(0.0, 1.0, 0.0);
        assertEquals(0.0, vectorY.dotProduct(vectorX), 1e-10,
                "Failed dot product with orthogonal Vectors");


        //TC11: test dot product with self(same object)

        // length squared is dot product of a vector
        assertEquals(vectorA.lengthSquared(), vectorA.dotProduct(vectorA), 1e-10,
                "Dot product with self(same object) failed");


        //TC12: Test dot product with opposite vector
        assertEquals(-14, vectorA.dotProduct(vectorNegative), "Dot product with opposite vector failed");


    }

    /** Test case for {@link primitives.Vector#crossProduct(Vector)} */

    @Test
    void crossProduct() {

        //============ Equivalence Partitions Tests ==============

        //TC01: test cross product standard

        Vector vectorA = new Vector(1.0, 2.0, 3.0);
        Vector vectorB = new Vector(4.0, 5.0, 6.0);
        assertEquals(new Vector(-3.0, 6.0, -3.0), vectorA.crossProduct(vectorB),
                "Failed test cross product standard");


        //TC02: test cross product with negative components

        Vector vectorNegative = new Vector(1.0, -2.0, 3.0);
        Vector vectorNegativeA = new Vector(-4.0, 5.0, -6.0);
        assertEquals(new Vector(-3.0, -6.0, -3.0),
                vectorNegative.crossProduct(vectorNegativeA),
                "Test cross product with negative components");


        //============ Boundary Partitions Tests ==============


        //TC10: test cross product with orthogonal Vectors

        Vector vectorX = new Vector(1.0, 0.0, 0.0);
        Vector vectorY = new Vector(0.0, 1.0, 0.0);
        assertEquals(new Vector(0.0, 0.0, 1.0), vectorX.crossProduct(vectorY),
                "Failed test cross product with orthogonal Vectors");


        //TC11: test cross product with self

        assertThrows(IllegalArgumentException.class, () -> vectorX.crossProduct(vectorX), "Vector equals to Zero Vector (0, 0, 0)");


    }

    /** Test case for {@link Vector#lengthSquared()} */

    @Test
    void lengthSquared() {

        //============ Equivalence Partitions Tests ==============

        //TC01: test Length Squared Standard

        Vector vector = new Vector(1.0, 2.0, 3.0);
        assertEquals(14.0, vector.lengthSquared(), 1e-10,
                "Failed test length squared standard");


        //TC02: test length squared with negative components

        Vector vectorNegative = new Vector(-1.0, -2.0, -3.0);
        assertEquals(14.0, vectorNegative.lengthSquared(), 1e-10,
                "Failed test length squared with negative components"); // Squared magnitude is the same regardless of signs


        //============ Boundary Partitions Tests ==============

    }


    /** Test case for {@link Vector#length()} */

    @Test
    void length() {

        //============ Equivalence Partitions Tests ==============


        //TC01: test length standard

        Vector vector = new Vector(1.0, 2.0, 3.0);
        assertEquals(Math.sqrt(14.0), vector.length(), 1e-10,
                "Failed test length standard");


        //TC02: test length with negative components

        Vector vectorNegative = new Vector(-1.0, -2.0, -3.0);
        assertEquals(Math.sqrt(14.0), vectorNegative.length(), 1e-10,
                "Failed test length with negative components");


        //============ Boundary Partitions Tests ==============

    }


    /** Test case for {@link Vector#normalize()} */

    @Test
    void normalize() {

        //============ Equivalence Partitions Tests ==============

        //TC01: test normalize standard

        Vector vector = new Vector(0.0, 4.0, 3.0);
        assertEquals(new Vector(0, 4.0 / 5, 3.0 / 5), vector.normalize(),
                " Failed test normalize standard");

        //TC02: test normalize with negative components

        Vector vectorNegative = new Vector(-4.0, 3.0, 0);
        assertEquals(new Vector(-4.0 / vectorNegative.length(), 3.0 / vectorNegative.length(), 0 / vectorNegative.length()), vectorNegative.normalize(),
                "Failed test normalize with negative components");


        //============ Boundary Partitions Tests ==============

        //TC10: test normalize unit Vector

        Vector unitVector = new Vector(1.0, 0.0, 0.0);
        assertEquals(unitVector, unitVector.normalize(),
                " Failed test normalize UnitVector");

    }
}
