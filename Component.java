package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static src.Constants.loadImage;

public class Component {
    public double leftEdge;
    public double topEdge;
    public double width;
    public double height;
    public String pathToImage;

    public Component(String pathToImage) {
        this.pathToImage = pathToImage;
        loadImage(pathToImage);
    }

    public double getRightEdge() {
        return leftEdge + width;
    }

    public double getBottomEdge() {
        return topEdge + height;
    }

    public double getVerticalMidpoint() {
        return topEdge + height / 2;
    }

    public double getHorizontalMidpoint() {
        return leftEdge + width / 2;
    }

    public void draw(Graphics graphics) {
//        BufferedImage image = Constants.pathToImage.get(pathToImage);
        try {
            BufferedImage image = ImageIO.read(new File(pathToImage));
            graphics.drawImage(image, (int) leftEdge, (int) topEdge, (int) width, (int) height, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {

    }

    public void setDimensions(double leftEdge, double topEdge, double width, double height) {
        this.leftEdge = leftEdge;
        this.topEdge = topEdge;
        this.width = width;
        this.height = height;
    }

}
