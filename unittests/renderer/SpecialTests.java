package renderer;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

public class SpecialTests {
    private final Scene scene      = new Scene("Test scene");
    /** Camera builder of the tests */
    private final Camera.Builder camera     = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0,1,0))
            .setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
            .setVpSize(200, 200)
            .setRayTracer(new SimpleRayTracer(scene));


    // This test produce a cube, sphere and a triangle. the cube built by 12 triangles
    @Test
    public void cubeMiniProject1() {
        double halfSideLength = 23.0f;
        double moveX = -30.0;
        double moveY = -40.0;
        double moveZ = 0.0;

        Point[] vertices = {
                new Point(50 - halfSideLength + moveX, 50 - halfSideLength + moveY, 50 - halfSideLength + moveZ), // V0
                new Point(50 + halfSideLength + moveX, 50 - halfSideLength + moveY, 50 - halfSideLength + moveZ), // V1
                new Point(50 + halfSideLength + moveX, 50 + halfSideLength + moveY, 50 - halfSideLength + moveZ), // V2
                new Point(50 - halfSideLength + moveX, 50 + halfSideLength + moveY, 50 - halfSideLength + moveZ), // V3
                new Point(50 - halfSideLength + moveX, 50 - halfSideLength + moveY, 50 + halfSideLength + moveZ), // V4
                new Point(50 + halfSideLength + moveX, 50 - halfSideLength + moveY, 50 + halfSideLength + moveZ), // V5
                new Point(50 + halfSideLength + moveX, 50 + halfSideLength + moveY, 50 + halfSideLength + moveZ), // V6
                new Point(50 - halfSideLength + moveX, 50 + halfSideLength + moveY, 50 + halfSideLength + moveZ)  // V7
        };

        Material cubeMaterial = new Material().setKd(0.3).setKr(0.8).setKs(0.4).setShininess(30);
        Color cubeSidesColor = new Color(GRAY);

        scene.geometries.add(
                new Triangle(
                        new Point(-150, -150, -65),
                        new Point(150, -150, -75),
                        new Point(75, 75, -90))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),

                new Triangle(
                        new Point(-150, -150, -55),
                        new Point(-70, 70, -80),
                        new Point(75, 75, -90))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),

                new Sphere(new Point(55, 50, -11), 22d) //
                        .setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                // CUBE (12 triangles - 2 for each side (6 * 2 = 12)):

                // Bottom face
                new Triangle(vertices[0], vertices[1], vertices[2]).setEmission(cubeSidesColor).setMaterial(cubeMaterial),
                new Triangle(vertices[0], vertices[2], vertices[3]).setEmission(cubeSidesColor).setMaterial(cubeMaterial),

                // Top face
                new Triangle(vertices[4], vertices[5], vertices[6]).setEmission(cubeSidesColor).setMaterial(cubeMaterial),
                new Triangle(vertices[4], vertices[6], vertices[7]).setEmission(cubeSidesColor).setMaterial(cubeMaterial),

                // Front face
                new Triangle(vertices[0], vertices[1], vertices[5]).setEmission(cubeSidesColor).setMaterial(cubeMaterial),
                new Triangle(vertices[0], vertices[5], vertices[4]).setEmission(cubeSidesColor).setMaterial(cubeMaterial),

                // Back face
                new Triangle(vertices[3], vertices[2], vertices[6]).setEmission(cubeSidesColor).setMaterial(cubeMaterial),
                new Triangle(vertices[3], vertices[6], vertices[7]).setEmission(cubeSidesColor).setMaterial(cubeMaterial),

                // Left face
                new Triangle(vertices[0], vertices[3], vertices[7]).setEmission(cubeSidesColor).setMaterial(cubeMaterial),
                new Triangle(vertices[0], vertices[7], vertices[4]).setEmission(cubeSidesColor).setMaterial(cubeMaterial),

                // Right face
                new Triangle(vertices[1], vertices[2], vertices[6]).setEmission(cubeSidesColor).setMaterial(cubeMaterial),
                new Triangle(vertices[1], vertices[6], vertices[5]).setEmission(cubeSidesColor).setMaterial(cubeMaterial),

                // bottom right corner triangle
                new Triangle(new Point(-30,-40,-15), new Point(10, -80, 30), new Point(-70, -80, 30)).setEmission(new Color(BLUE))
        );

        scene.setAmbientLight(new AmbientLight(new Color(pink), 0.15));

        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(50, 50, 250),
                        new Vector(-1, -1, -4))
                        .setKl(4E-4).setKq(2E-5).setRadius(4d)
        );

        scene.lights.add(
                new PointLight(new Color(180, 180, 180), new Point(-25, -45, 100))
                        .setKl(4E-4).setKq(2E-5).setRadius(4d)
        );

        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(-70, -115, 80), new Vector(1, 1, -4))
                .setKl(0.001).setKq(0.0002).setRadius(4d));

        scene.lights.add(
                new DirectionalLight(new Color(190, 190, 190), new Vector(3, -1, -4))
        );


        // To see the cube better (see 3 sides of the cube)
        camera.transform(new Point(210, 230, 950), new Point(0, 0, 0), 180);
        //camera.setMultithreading(5);
        //camera.setSoftShadows(true);
        camera.setDensity(9);
////
        camera.setSuperSampling(true);

        // Render the image
        camera.setImageWriter(new ImageWriter("cube-AntiAliasing&SoftShadows-4x4-R4", 600, 600))
                .build()
                .renderImage();
        camera.build().writeToImage();
    }


    // This test produce 10 random geometries - built this test to show the AntiAliasing
    @Test
    public void randomGeometriesMiniProject1() {

        scene.geometries.add(
                new Polygon(
                        new Point(0, 0, 0),
                        new Point(20, 20, 0),
                        new Point(0, 40, 0)
                ).setEmission(new Color(75,0,130))
                        .setMaterial(new Material().setShininess(10).setKt(0.001).setKs(0.02)),

                new Sphere(new Point(-40, 0, 300), 40)
                        .setMaterial(new Material().setShininess(20).setKt(0.5)).setEmission(new Color(BLUE)),

                new Plane(
                        new Point(-50,0,-50),
                        new Vector(0,0,1)
                ).setMaterial(new Material().setShininess(20).setKr(0.7)),

                new Plane(
                        new Point(-150, -150, -115),
                        new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setEmission(new Color(75,0,130))
                        .setMaterial(new Material().setKs(0.8).setKr(0.8).setShininess(60)),

                new Sphere(new Point(-40, 0, -11), 20d)
                        .setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),

                new Sphere(new Point(0, 1, -11), 20d) //
                        .setEmission(new Color(YELLOW)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.6).setKt(0.6).setShininess(30)),

                new Sphere(new Point(40, 2, -11), 20d) //
                        .setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setKt(0.9).setShininess(20)),

                new Polygon(
                        new Point(40, 30, 200),
                        new Point(60, 30, 200),
                        new Point(70, 40, 200),
                        new Point(70, 60, 200),
                        new Point(40, 60, 200))
                        .setMaterial(new Material().setKd(0.2).setKs(0.6).setShininess(30)).setEmission(new Color(GREEN)),

                new Triangle(
                        new Point(100, -50, 300),
                        new Point(50,-50,350),
                        new Point(0,0,400))
                        .setMaterial(new Material().setKd(0.2).setKs(0.01).setKt(0.6).setShininess(15)).setEmission(new Color(YELLOW))
        );

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(0, 30, 115), new Vector(-1, -1, -4)) //
                        .setKl(4E-4).setKq(2E-5));

        scene.lights.add(new PointLight(new Color(500, 400, 400), new Point(80, 40, 115)) //
                .setKl(4E-4).setKq(2E-5));

        scene.lights.add(new DirectionalLight(new Color(500, 350, 800), new Vector(1,1,0.5)));

        camera.setDensity(17);
        camera.setImageWriter(new ImageWriter("MiniP-AntiAliasing-17x17", 600, 600))
                .build()
                .renderImage();

        camera.build().writeToImage();
    }
}
