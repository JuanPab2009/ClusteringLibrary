package com.clustering;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para cargar datos desde imágenes.
 */
public class ImageDataLoader {

    /**
     * Carga datos desde una imagen, convirtiendo los píxeles en DataPoints.
     *
     * @param imagePath Ruta de la imagen.
     * @return Lista de puntos de datos.
     * @throws IOException Si ocurre un error al leer la imagen.
     */
    public List<DataPoint> loadFromImage(String imagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        List<DataPoint> dataPoints = new ArrayList<>();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                // Cambiar double[] a Object[]
                Object[] features = { (double) color.getRed(), (double) color.getGreen(), (double) color.getBlue() };
                dataPoints.add(new DataPoint(features));
            }
        }
        return dataPoints;
    }

    // Resto de la clase...
}
