package com.urbanpulse.util;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

    private static final int MAX_DIMENSION = 1024;

    private ImageUtil() {}

    public static void resizeInPlace(File imageFile) throws IOException {
        BufferedImage original = ImageIO.read(imageFile);
        if (original == null) {
            throw new IOException("Not a valid image file: " + imageFile.getName());
        }

        int width = original.getWidth();
        int height = original.getHeight();
        if (width <= MAX_DIMENSION && height <= MAX_DIMENSION) {
            return;
        }

        double scale = Math.min((double) MAX_DIMENSION / width, (double) MAX_DIMENSION / height);
        int newWidth = (int) (width * scale);
        int newHeight = (int) (height * scale);

        Image scaledImage = original.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        resized.getGraphics().drawImage(scaledImage, 0, 0, null);

        String formatName = imageFile.getName().substring(imageFile.getName().lastIndexOf('.') + 1);
        ImageIO.write(resized, formatName, imageFile);
    }

    public static boolean isValidImageFormat(String fileName) {
        String lower = fileName.toLowerCase();
        return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png");
    }
}