package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

public class Scene {

    /**
     * Scene constructor to initialize the scene name
     * @param name name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }


    // * All setters return this as Builder *

    // Setter for ambientLight
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    // Setter for background color
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    // Setter for geometries
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }


    /** Name os the scene */
    public String name;

    /** Background color of the scene */
    public Color background = Color.BLACK;

    /** AmbientLight - initialize to black by default*/
    public AmbientLight ambientLight = AmbientLight.NONE;

    /** Geometries for the scene */
    public Geometries geometries = new Geometries();
}