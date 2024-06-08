package geometries;
import primitives.Color;
import primitives.Material;
import primitives.Vector;
import primitives.Point;

/**
 * Geometry interface for all geometry related
 */
public abstract class Geometry extends Intersectable {

    /** emission of geometry */
    protected Color emission = Color.BLACK;

    /** represents material on geometry */
    private Material material = new Material();

    /**
     * Setter for material
     * @param material a new Material object to set to
     * @return this - current object
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Setter for emission
     * @param emission the new emission color
     * @return this - current object
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    // Getter for material
    public Material getMaterial() { return material; }

    // Getter for emission
    public Color getEmission() { return emission; }


    /**
     * getNormal function that calculate the normal with different geometrical shapes (implemented for classes the implements the interface)
     * @param p point to calculate the normal with
     * @return The normal vector
     */
    public abstract Vector getNormal(Point p);
}