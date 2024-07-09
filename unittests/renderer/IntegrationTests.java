package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import scene.Scene;


public class IntegrationTests {
    /**private string with error */
    private final String error = "ERROR: wrong intersection";
    /** private builder to create cameras*/
    private Camera.Builder builder = new Camera.Builder();

    /**
     * private helper function that tells us how many intersections will appear from a camera
     * @param builder builder to finish building the camera
     * @param geometry the object to calculate intersections for
     * @param nX
     * @param nY
     * @return the actual amount of intersections
     */
    private int countIntersections(Camera.Builder builder, Intersectable geometry, int nX, int nY){
        // build the camera with a view plane of 3x3 and distance of 1
        Camera camera = builder.setVpSize(3,3)
                .setVpDistance(1)
                .setImageWriter(new ImageWriter("test construct Ray", 500, 800))
                .setRayTracer(new SimpleRayTracer(new Scene("name")))
                .build();

        int counter = 0;
        //List to hold intersections
        List<Point> intersections = null;

        //loop through all pixels
        for (int i = 0; i < nY; i++){
            for (int j = 0; j < nX; j++){
                intersections = geometry.findIntersections(camera.constructRay(nX, nY, j, i));
                if(intersections != null) { counter += intersections.size(); }

            }
        }

        return counter;
    }


    /** test ray integration with camera and finding intersections on sphere. */

    @Test
    public void sphereIntegrationTest () {
        //TC01 sphere with radius 1
        builder = builder.setLocation(Point.ZERO).setDirection(new Vector(0,0,-1), new Vector(0,1,0));
        assertEquals(2, countIntersections(builder,
                new Sphere(new Point(0,0,-3), 1), 3, 3), error);

        //TC02 sphere with radius 2.5
        builder = builder.setLocation(new Point(0,0,0.5));
        assertEquals(18, countIntersections(builder,
                new Sphere(new Point(0,0,-2.5), 2.5), 3, 3), error);

        //TC03 sphere with radius 2
        assertEquals(10, countIntersections(builder,
                new Sphere(new Point(0,0,-2), 2), 3, 3), error);

        //TC04 sphere with radius 4
        assertEquals(9, countIntersections(builder,
                new Sphere(new Point(0, 0, -1), 4), 3, 3), error);

        //TC05 sphere with radius 0.5
        assertEquals(0, countIntersections(builder,
                new Sphere(new Point(0, 0, 1), 0.5), 3, 3), error);

    }


    /** test ray integration with camera and finding intersections on plane. */

    @Test
    public void planeIntegrationTest () {
        builder = builder.setLocation(Point.ZERO).setDirection(new Vector(0,0,-1), new Vector(0,1,0));

        //TC01 parallel plane with 9 intersections
        assertEquals(9, countIntersections(builder,
                new Plane(
                        new Point(0, 0, -5),
                        new Vector(0,0,1)
                ),
                3, 3), error);

        //TC02 plane at small angle with 9 intersections
        assertEquals(9, countIntersections(builder,
                new Plane(
                    new Point(0, 0, -5),
                    new Vector(0,1,2)
                ),
                3, 3), error);

        //TC03 plane at bigger angle with 6 intersections
        assertEquals(6, countIntersections(builder,
                new Plane(
                        new Point(0, 0, -5),
                        new Vector(0,1,1)
                ),
                3, 3), error);
    }


    /** test ray integration with camera and finding intersections on triangle. */

    @Test
    public void triangleIntegrationTest () {
        builder = builder.setLocation(Point.ZERO).setDirection(new Vector(0,0,-1), new Vector(0,1,0));

        //TC01 Triangle with one intersection
        assertEquals(1, countIntersections(builder,
                new Triangle(
                        new Point(1, -1, -2),
                        new Point(-1,-1,-2),
                        new Point(0,1,-2)
                ),
                3, 3), error);

        //TC02 with 2 intersections
        assertEquals(2, countIntersections(builder,
                new Triangle(
                        new Point(1, -1, -2),
                        new Point(-1,-1,-2),
                        new Point(0,20,-2)
                ),
                3, 3), error);
    }
}
