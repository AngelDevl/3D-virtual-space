package scene;

import com.google.gson.*;
import primitives.*;
import geometries.*;
import lighting.AmbientLight;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;


class PointDeserializer implements JsonDeserializer<Point> {
    @Override
    public Point deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        double x = jsonObject.get("x").getAsDouble();
        double y = jsonObject.get("y").getAsDouble();
        double z = jsonObject.get("z").getAsDouble();
        return new Point(x, y, z);
    }
}

class Double3Deserializer implements JsonDeserializer<Double3> {
    @Override
    public Double3 deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        double x = jsonObject.get("x").getAsDouble();
        double y = jsonObject.get("y").getAsDouble();
        double z = jsonObject.get("z").getAsDouble();
        return new Double3(x, y, z);
    }
}

class ColorDeserializer implements JsonDeserializer<Color> {
    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        double r = jsonObject.get("r").getAsDouble();
        double g = jsonObject.get("g").getAsDouble();
        double b = jsonObject.get("b").getAsDouble();
        return new Color(r, g, b);
    }
}

public class SceneLoader {

    public static void loadSceneFromJson(Scene scene, String filePath) throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Register the custom deserializers
        gsonBuilder.registerTypeAdapter(Point.class, new PointDeserializer());
        gsonBuilder.registerTypeAdapter(Double3.class, new Double3Deserializer());
        gsonBuilder.registerTypeAdapter(Color.class, new ColorDeserializer());

        Gson gson = gsonBuilder.create();

        // Parse the JSON file
        JsonObject rootNode = JsonParser.parseReader(new FileReader(filePath)).getAsJsonObject();

        // Parse geometries
        JsonArray geometriesArray = rootNode.getAsJsonArray("geometries");
        for (JsonElement geometryElement : geometriesArray) {
            JsonObject geometryObject = geometryElement.getAsJsonObject();
            String type = geometryObject.get("type").getAsString();
            switch (type) {
                case "Sphere":
                    Point center = gson.fromJson(geometryObject.get("center"), Point.class);
                    double radius = geometryObject.get("radius").getAsDouble();
                    Sphere sphere = new Sphere(center, radius);
                    scene.geometries.add(sphere);
                    break;

                case "Triangle":
                    Point[] vertices = gson.fromJson(geometryObject.get("vertices"), Point[].class);
                    Triangle triangle = new Triangle(vertices[0], vertices[1], vertices[2]);
                    scene.geometries.add(triangle);
                    break;
            }
        }

        // Parse ambient light
        JsonObject ambientLightObject = rootNode.getAsJsonObject("ambientLight");
        Color ambientColor = gson.fromJson(ambientLightObject.get("color"), Color.class);
        Double3 intensity = gson.fromJson(ambientLightObject.get("intensity"), Double3.class);
        AmbientLight ambientLight = new AmbientLight(ambientColor, intensity);
        scene.setAmbientLight(ambientLight);

        // Parse background
        Color backgroundColor = gson.fromJson(rootNode.get("background"), Color.class);
        scene.setBackground(backgroundColor);
    }
}