package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class for camera - used for rendering the image
 */
public class Camera implements Cloneable {

    /** Camera location */
    private Point location;

    /**
     * vecTo - points in the direction the camera looking
     * vecUp - defines which direction is "up" from the camera's perspective
     * vecRight - points to the right of the camera's view (to calculate - crossProduct of vecTo and vecUp)
     */
    private Vector vecUp, vecRight, vecTo;

    /** height of view plane */
    private double height = 0;
    /** distance of camera from view plane */
    private double distance = 0;
    /** width of view plane */
    private double width = 0;

    /** density of rays, represented by square root of actual density */
    private int density = 1;

    /** blackboard to use for each pixel */
    private Blackboard blackboard;

    /** Plane center point */
    private Point viewPlaneCenter;

    /** ImageWriter object to write the rendered image to the disk */
    ImageWriter imageWriter;

    /** RayTracer object for tracing the rays */
    private RayTracerBase rayTracer;

    /** Pixel manager object to manage what pixel should each thread render */
    private PixelManager pixelManager;

    /** How many threads to create */
    private int threadsCount = 0; // -2 auto, -1 range/stream, 0 no threads, 1+ number of threads

    /** Spare threads in case of all cores */
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores

    /** Print interval for percentage */
    private double printInterval = 0;    // printing progress percentage interval

    /**
     * Boolean value for whether to use superSampling or not.
     * We need a boolean and recursion depth variables since the recursion depth depends on the density value,
     * and the user can set the superSampling before setting the density and then the superSampling would not work.
     */
    private boolean useSuperSampling = false;

    /** Represents recursion depth for superSampling */
    private int superSampling = 0;


    private Camera() {

    }

    @Override
    public Camera clone() {
        try {
            return (Camera) super.clone();

        } catch (CloneNotSupportedException exp) {
            throw new AssertionError();
        }
    }

    /**
     * constructRay function construct a ray from the camera into the middle of a pixel
     * @param nX row length - define how much pixels are horizontally
     * @param nY column height - define how much pixels are vertically
     * @param j pixel index - column (x)
     * @param i pixel index - row (y)
     * @return A ray pointing to the middle of the pixel from the camera
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        double Rx = width / nX; // The width of a single pixel
        double Ry = height / nY; // The height of a single pixel

        double xj = (j - (nX - 1) / 2d) * Rx;
        double yi = - (i - (nY - 1) / 2d) * Ry;
        Point pij = viewPlaneCenter;

        if(!isZero(xj))
            pij = pij.add(vecRight.scale(xj));

        if(!isZero(yi))
            pij = pij.add(vecUp.scale(yi));

        return new Ray(location, pij.subtract(location));
    }

    /**
     * constructs a ray/rays through a pixel using blackboard generateJitterGrid function
     * to generate multiple points in a grid by moving them with a random factor
     * @param nX row length
     * @param nY column height
     * @param j pixel index - column
     * @param i pixel index - row
     * @return list of rays
     */
    public List<Ray> constructRays(int nX, int nY, int j, int i){
        List<Ray> rays = new LinkedList<>();

        //Ratio width and height
        double Ry = height / nY;
        double Rx = width / nX;

        // Pixel[i,j] center
        double xJ = (j - (nX - 1) / 2.0) * Rx;
        double yI = -(i - (nY - 1) / 2.0) * Ry;

        Point Pij = viewPlaneCenter;
        // make sure to account for Vector ZERO
        if (!isZero(xJ)) { Pij = Pij.add(vecRight.scale(xJ)); }
        if (!isZero(yI)) { Pij = Pij.add(vecUp.scale(yI)); }

        if (blackboard.getDensity() != 1) {
            // set center point to center of pixel and set width and height
            blackboard = blackboard.setCenterPoint(Pij).setWidth(Rx).setHeight(Ry);
            // generate list of points on grid
            List<Point> points = blackboard.generateJitterGrid();
            for (Point point : points){
                // rays to point on grid
                rays.add(new Ray(location, point.subtract(location)));
            }
        }
        else {
            Vector Vij = Pij.subtract(location);
            rays.add(new Ray(location, Vij));
        }

        return rays;
    }

    /**
     * calculates Color of pixel using adaptive supersampling
     * @param Nx number of columns
     * @param Ny number of rows
     * @param i pixel index we are on
     * @param j pixel index we are on
     */
    private Color constructRayAccelerator(int Nx, int Ny, int j, int i) {
        //Image Center

        //Ratio width and height
        double Ry = height / Ny;
        double Rx = width / Nx;

        //Pixel[i,j] center
        double yI = -(i - (Ny-1) / 2.0) * Ry;
        double xJ =  (j - (Nx-1) / 2.0) * Rx;
        Point Pij = viewPlaneCenter;
        //make sure to account for Vector ZERO
        if(!isZero(xJ)) { Pij = Pij.add(vecRight.scale(xJ)); }
        if(!isZero(yI)) { Pij = Pij.add(vecUp.scale(yI)); }

        Blackboard blackboard1 = new Blackboard(vecUp, vecRight, 1);
        return constructRayAcceleratorHelper(Pij, Rx, Ry, superSampling, blackboard1, Color.BLACK);
    }

    /**
     * assisting function for superSampling, calculates color
     * @param Pij center point of target area
     * @param Rx width of target area
     * @param Ry height of target area
     * @param depth recursive max depth
     * @param blackboard1 blackboard item, used to optimize creation of objects
     * @param color color to average with
     * @return final pixel color
     */
    private Color constructRayAcceleratorHelper(Point Pij, double Rx, double Ry, int depth, Blackboard blackboard1, Color color){
        blackboard1 = blackboard1.setCenterPoint(Pij).setWidth(Rx).setHeight(Ry);
        List<Point> points = blackboard1.generateGrid();
        List<Ray> rays = new LinkedList<>();
        for(Point point : points){
            //rays to point on grid
            rays.add(new Ray(location, point.subtract(location)));
        }
        Color avgColor = calcAvgColor(rays);

        Color color0 = rayTracer.traceRay(rays.getFirst());
        if(color0.equalsMarg(avgColor)|| depth < 1){
            if(color.equals(Color.BLACK))
                color = color.add(avgColor);
            else
                color = color.add(avgColor).reduce(2);

            return color;
        }
        color = constructRayAcceleratorHelper(Pij.add(vecRight.scale(-0.25*Rx)).add(vecUp.scale(0.25*Ry)), Rx/2d, Ry/2d, (depth-1), blackboard1, color);
        color = constructRayAcceleratorHelper(Pij.add(vecRight.scale(0.25*Rx)).add(vecUp.scale(0.25*Ry)), Rx/2d, Ry/2d, (depth-1), blackboard1, color);
        color = constructRayAcceleratorHelper(Pij.add(vecRight.scale(0.25*Rx)).add(vecUp.scale(-0.25*Ry)), Rx/2d, Ry/2d, (depth-1), blackboard1, color);
        color = constructRayAcceleratorHelper(Pij.add(vecRight.scale(-0.25*Rx)).add(vecUp.scale(-0.25*Ry)), Rx/2d, Ry/2d, (depth-1), blackboard1, color);
        return color;
    }

    /**
     * calculates the average color of a list of points
     * @param rays list of rays to calculate color for
     * @return averaged color
     */
    private Color calcAvgColor(List<Ray> rays){
        Color color = Color.BLACK;
        for (Ray ray : rays) {
            color = color.add(rayTracer.traceRay(ray));
        }

        return color.reduce(rays.size());
    }

    /**
     * Looping over all the pixels and using castRay function on every pixel to create a ray to the pixel to find the color
     * and write the color to the pixel
     */
    public Camera renderImage() {
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();

        pixelManager = new PixelManager(nX, nY, printInterval);

        if (threadsCount == 0) {
            for (int i = 0; i < nX; ++i) {
                for (int j = 0; j < nY; ++j) {
                    castRays(nX, nY, i, j);
                }
            }
        }
        else if (threadsCount == -1) {
            IntStream.range(0, nY).parallel()
                    .forEach(i -> IntStream.range(0, nX).parallel()
                            .forEach(j -> castRays(nX, nY, j, i)));
        } else {
            var threads = new LinkedList<Thread>();
            while (threadsCount-- > 0) {
                threads.add(new Thread(() -> {
                    PixelManager.Pixel pixel;
                    while ((pixel = pixelManager.nextPixel()) != null) {
                        castRays(nX, nY, pixel.col(), pixel.row());
                    }
                }));
            }

            for (var thread : threads) {
                thread.start();
            }

            try {
                for (var thread : threads) thread.join();
            } catch (InterruptedException ignore) {}
        }

        return this;
    }

    /**
     * construct multiple rays in the given row and column and find the color of the pixel
     * using calcAvgColor that calls trace ray and calculate the average color in a List of rays.
     * after we find the average color we write the color to the right pixel
     * @param nX width
     * @param nY height
     * @param i row of the pixel
     * @param j column of the pixel
     */
    private void castRays(int nX, int nY, int i, int j) {
        if (superSampling > 0) {
            imageWriter.writePixel(i, j, constructRayAccelerator(nX, nY, i, j));
        }
        else {
            imageWriter.writePixel(i, j, calcAvgColor(constructRays(nX, nY, i, j)));
        }

        pixelManager.pixelDone();
    }

    /**
     * construct a ray in the given row and column and find the color of the pixel using traceRay.
     * after we find the color we write the color to the right pixel
     * @param nX width
     * @param nY height
     * @param i row of the pixel
     * @param j column of the pixel
     */
    private void castRay(int nX, int nY, int i, int j) {
        Ray ray = constructRay(nX, nY, i, j);
        imageWriter.writePixel(i, j, rayTracer.traceRay(ray));
        pixelManager.pixelDone();
    }



    /**
     * * This is much faster than the imageWriterTest since we are not running on all the pixels
     * printGrid function to print grid around the image objects using interval
     * @param interval interval for when to draw the grid color
     * @param color the color that we would draw the grid pixels
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null) throw new MissingResourceException("Missing image writer.", "", "");

        for (int i = 0; i < imageWriter.getNx(); i += interval) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, color);
            }
        }

        for (int j = 0; j < imageWriter.getNy(); j += interval) {
            for (int i = 0; i < imageWriter.getNx(); i++) {
                imageWriter.writePixel(i, j, color);
            }
        }
    }

    /**
     * Calling the imageWriter writeToImage function
     */
    public void writeToImage() {
        this.imageWriter.writeToImage();
    }

    /** @return current camera location*/
    public Point getLocation() { return location; }

    /** @return camera up direction vector*/
    public Vector getVecUp() { return vecUp; }

    /** @return camera right direction vector*/
    public Vector getVecRight() { return vecRight; }

    /** @return camera to direction vector*/
    public Vector getVecTo() { return vecTo; }

    /** @return camera height*/
    public double getHeight() { return height; }

    /** @return camera distance from the view plane*/
    public double getDistance() { return distance; }

    /** @return camera width*/
    public double getWidth() { return width; }

    /**
     * Getter for Builder object
     * @return A new Builder object
     */
    public static Builder getBuilder() { return new Builder(); }

    /**
     * nested builder class to build the camera
     */
    public static class Builder {

        /**
         * This function set new camera position with rotation (transformation)
         *
         * @param location - the new position
         * @param target      - the target point
         * @param angle       - the angle of rotation
         * @return the camera after set the new position
         */
        public Builder transform(Point location, Point target, double angle) {
            camera.location = location;
            camera.vecTo = target.subtract(location).normalize();

            try {
                camera.vecRight = camera.vecTo.crossProduct(Vector.y).normalize();
                camera.vecUp = camera.vecTo.crossProduct(camera.vecRight).normalize();

            } catch (IllegalArgumentException e) {
                camera.vecUp = Vector.z;
                camera.vecRight = camera.vecTo.crossProduct(camera.vecUp).normalize();
            }

            return angle == 0 ? this : rotateCamera(angle);
        }

        /**
         * This function rotate the camera by a given angle by updating the vecTo
         *
         * @param angle - the angle of rotation
         * @return the camera after set the new position
         */
        public Builder rotateCamera(double angle) {
            camera.vecUp = camera.vecUp.vectorRotate(camera.vecTo, Math.toRadians(angle));
            camera.vecRight = camera.vecUp.crossProduct(camera.vecTo).normalize();
            return this;
        }


        /**
         * Sets the location of the camera
         * @param location the new location of the camera
         * @return the updated this object
         */
        public Builder setLocation(Point location) {
            camera.location = location;
            return this;
        }

        /**
         * Setting the direction of vecTo and vecRight and ensure that they are orthogonal and normalized
         * @param vecTo a vector that points in the direction the camera looking
         * @param vecUp upward direction vector
         * @return the updated this object
         */
        public Builder setDirection(Vector vecTo, Vector vecUp) {
            if (!isZero(alignZero(vecUp.dotProduct(vecTo)))) {
                throw new IllegalArgumentException("Vectors mush be orthogonal");
            }

            camera.vecTo = vecTo.normalize();
            camera.vecUp = vecUp.normalize();

            return this;
        }

        /**
         * Sets the view plane size
         * @param width width of the view plane
         * @param height height of the view plane
         * @return the updated this object
         */
        public Builder setVpSize(double width, double height) {
            camera.width = width;
            camera.height = height;

            return this;
        }

        /**
         * Sets the view plane distance from the camera
         * @param distance the distance between the view plane and the camera
         * @return the updated this object
         */
        public Builder setVpDistance(double distance) {
            camera.distance = distance;
            return this;
        }

        /**
         * sets density of anti aliasing
         * @param density density represented in square root (if var is 2 then we will have 4 beams)
         * @return the updated this object
         */
        public Builder setDensity(int density){
            camera.density = density;
            return this;
        }

        /**
         * Sets useSuperSampling - true -> use superSampling else don't.
         * We need both boolean and recursion depth superSampling variables,
         * since the user can enable the superSampling before setting the density.
         * And then the superSampling would not work since it depends on the density value.
         * @return the updated this object
         */
        public Builder setSuperSampling(boolean useSuperSampling) {
            camera.useSuperSampling = useSuperSampling;
            return this;
        }

        /**
         * Sets whether to use soft shadows or not
         * @param useSoftShadows boolean value for useSoftShadows
         * @return the updated this object
         */
        public Builder setSoftShadows(boolean useSoftShadows) {
            ((SimpleRayTracer) camera.rayTracer).setUseSoftShadows(useSoftShadows);
            return this;
        }

        /**
         * sets multithreading amount
         * @param threads amount of threads
         * @return the updated this object
         */
        public Builder setMultithreading(int threads) {
            if (threads < -2)
                throw new IllegalArgumentException("Multithreading must be -2 or higher");

            if (threads >= -1) {
                camera.threadsCount = threads;
            } else { // == -2
                int cores = Runtime.getRuntime().availableProcessors() - camera.SPARE_THREADS;
                camera.threadsCount = cores <= 2 ? 1 : cores;
            }

            return this;
        }

        /**
         * progress bar, only works in Eclipse
         * @param interval how often should it update
         * @return the updated this object
         */
        public Builder setDebugPrint(double interval) {
            camera.printInterval = interval;
            return this;
        }

        /**
         * Sets the ImageWriter for the camera
         * @param imageWriter ImageWriter object
         * @return the updated this object
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the ray tracer
         * @param rayTracer RayTracerBase object
         * @return the updated this object
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * build function initializing camera properties and making sure all the needed properties are initialized
         * @return A processed camera object
         */
        public Camera build() {
            final String errorPrefix = "Render data in missing";
            final String errorSuffix = " is not initialized.";
            final String className = "Camera";

            if (isZero(camera.height)) throw new MissingResourceException(errorPrefix, className, "height" + errorSuffix);
            if (isZero(camera.width)) throw new MissingResourceException(errorPrefix, className, "width" + errorSuffix);
            if (isZero(camera.distance)) throw new MissingResourceException(errorPrefix, className, "distance" + errorSuffix);
            if (camera.imageWriter == null) throw new MissingResourceException(errorPrefix, className, "imageWriter" + errorSuffix);
            if (camera.rayTracer == null) throw new MissingResourceException(errorPrefix, className, "rayTracer" + errorSuffix);

            camera.vecRight = (camera.vecTo.crossProduct(camera.vecUp)).normalize();
            camera.viewPlaneCenter = camera.location.add(camera.vecTo.scale(camera.distance));

            // calculates recursion depth by rounding as if we would scan the whole pixel as opposed to just parts of it
            if (camera.useSuperSampling)
                camera.superSampling = (int)(Math.log(camera.density) / Math.log(2));

            camera.blackboard = new Blackboard(camera.vecUp, camera.vecRight, camera.density);

            return (Camera) camera.clone();
        }

        private final Camera camera = new Camera();
    }
}