package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene class - holds all the components of the current scene including the lighting objects and geometries objects
 */
public class Scene {

    /** Name os the scene */
    public String name;

    /** Background color of the scene */
    public Color background = Color.BLACK;

    /** AmbientLight - initialize to black by default*/
    public AmbientLight ambientLight = AmbientLight.NONE;

    /** Geometries for the scene */
    public Geometries geometries = new Geometries();

    /** List of all the lights that would be represented in the scene */
    public List<LightSource> lights = new LinkedList<>();


    /**
     * Scene constructor to initialize the scene name
     * @param name name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }


    // * All setters return this as Builder *

    /**
     * Setter for ambientLight
     * @param ambientLight new AmbientLight
     * @return this - current object
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Setter for background
     * @param background new background color
     * @return this - current object
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Setter for geometries
     * @param geometries new Geometries object
     * @return this - current object
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Setter for lights
     * @param lights list of LightSource objects
     * @return this - current object
     */
    public Scene setLightSources(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}