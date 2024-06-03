package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class IntegrationTests {
    /**private string with error */
    private final String error = "ERROR: wrong intersection";
    /** private builder to create cameras*/
    private Camera.Builder builder = new Camera.Builder();

    /**
     * private helper function that tells us how many intersections will appear from a camera
     * @param builder builder to finish building the camera
     * @param geometry the object to calculate intersections for
     * @return the actual amount of intersections
     */
    private int intersectAmount(Camera.Builder builder, Intersectable geometry){
        // build the camera with a view plane of 3x3 and distance of 1
        Camera cam = builder.setVpSize(3,3).setVpDistance(1).build();
        // width
        int nX = 3;
        // height
        int nY = 3;
        //counter
        int counter = 0;
        //List to hold intersections
        List<Point> intersectList = null;

        //loop through all pixels
        for (int i = 0; i < nY; i++){
            for (int j = 0; j < nX; j++){
                intersectList = geometry.findIntersections(cam.constructRay(nX, nY, j, i));
                if(intersectList != null) { counter += intersectList.size(); }

            }
        }

        return counter;
    }

    /** test ray integration with camera and finding intersections on sphere. */
    @Test
    public void sphereIntegrationTest () {
        //TC01 sphere with radius 1
        builder = builder.setLocation(Point.ZERO).setDirection(new Vector(0,0,-1), new Vector(0,1,0));
        assertEquals(2, intersectAmount(builder, new Sphere(new Point(0,0,-3), 1)), error);

        //TC02 sphere with radius 2.5
        builder = builder.setLocation(new Point(0,0,0.5));
        assertEquals(18, intersectAmount(builder, new Sphere(new Point(0,0,-2.5), 2.5)), error);

        //TC03 sphere with radius 2
        assertEquals(10, intersectAmount(builder, new Sphere(new Point(0,0,-2), 2)), error);

        //TC04 sphere with radius 4
        assertEquals(9, intersectAmount(builder, new Sphere(new Point(0, 0, -1), 4)), error);

        //TC05 sphere with radius 0.5
        assertEquals(0, intersectAmount(builder, new Sphere(new Point(0, 0, 1), 0.5)), error);

    }

    /** test ray integration with camera and finding intersections on plane. */
    @Test
    public void planeIntegrationTest () {
        builder = builder.setLocation(Point.ZERO).setDirection(new Vector(0,0,-1), new Vector(0,1,0));

        //TC01 parallel plane with 9 intersections
        assertEquals(9, intersectAmount(builder, new Plane(new Point(0, 0, -5), new Vector(0,0,1))), error);

        //TC02 plane at small angle with 9 intersections
        assertEquals(9, intersectAmount(builder, new Plane(new Point(0, 0, -5), new Vector(0,1,2))), error);

        //TC03 plane at bigger angle with 6 intersections
        assertEquals(6, intersectAmount(builder, new Plane(new Point(0, 0, -5), new Vector(0,1,1))), error);
    }

    /** test ray integration with camera and finding intersections on triangle. */
    @Test
    public void triangleIntegrationTest () {
        builder = builder.setLocation(Point.ZERO).setDirection(new Vector(0,0,-1), new Vector(0,1,0));

        //TC01 Triangle with one intersection
        assertEquals(1, intersectAmount(builder, new Triangle(new Point(1, -1, -2), new Point(-1,-1,-2), new Point(0,1,-2))), error);

        //TC02 with 2 intersections
        assertEquals(2, intersectAmount(builder, new Triangle(new Point(1, -1, -2), new Point(-1,-1,-2), new Point(0,20,-2))), error);
    }
}
