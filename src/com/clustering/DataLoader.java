package com.clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para cargar datos desde archivos.
 */
public class DataLoader {

    /**
     * Carga datos desde un archivo CSV.
     *
     * @param filePath Ruta del archivo CSV.
     * @return Lista de puntos de datos.
     * @throws IOException           Si ocurre un error al leer el archivo.
     * @throws InvalidDataException  Si los datos son inválidos.
     */
    public List<DataPoint> loadFromCSV(String filePath) throws IOException, InvalidDataException {
        List<DataPoint> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Object[] features = new Object[values.length];
                for (int i = 0; i < values.length; i++) {
                    try {
                        // Intentar parsear a Double
                        features[i] = Double.parseDouble(values[i]);
                    } catch (NumberFormatException e) {
                        // Si no es un número, guardarlo como String (categórico)
                        features[i] = values[i];
                    }
                }
                data.add(new DataPoint(features));
            }
        }
        return data;
    }
}

