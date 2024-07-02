package renderer;
import primitives.Color;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;
import static java.awt.Color.yellow;

public class ImageWriterTest {

    @Test
    void ImageWriterTests() {

        // Test for a 10x16 image with a grid

        int width = 800, height = 500;
        ImageWriter imageWriter = new ImageWriter("test", width, height);
        Color yellow = new Color(YELLOW);
        Color red = new Color(RED);


        // One square is 50x50 pixels
        // so every time x or y index is on the edge of the square we write a red color to that pixel
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i % 50 == 0 || j % 50 == 0)
                    imageWriter.writePixel(i, j, red);
                else
                    imageWriter.writePixel(i, j, yellow);
            }
        }

        // Write the image to the disk
        imageWriter.writeToImage();
    }
}