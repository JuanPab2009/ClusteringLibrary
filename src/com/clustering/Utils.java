package com.clustering;

/**
 * Clase de utilidades con métodos estáticos.
 */
public final class Utils {

    private Utils() {
        // Constructor privado para evitar instanciación
    }

    /**
     * Calcula la distancia entre dos vectores de características según la métrica especificada.
     *
     * @param features1 Primer vector de características.
     * @param features2 Segundo vector de características.
     * @param metric    Métrica de distancia a utilizar.
     * @return Distancia calculada.
     */
    public static double calculateDistance(double[] features1, double[] features2, DistanceMetric metric) {
        switch (metric) {
            case EUCLIDEAN:
                return euclideanDistance(features1, features2);
            case MANHATTAN:
                return manhattanDistance(features1, features2);
            case COSINE:
                return cosineDistance(features1, features2);
            default:
                throw new IllegalArgumentException("Métrica de distancia no soportada.");
        }
    }

    private static double euclideanDistance(double[] x, double[] y) {
        double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            sum += Math.pow(x[i] - y[i], 2);
        }
        return Math.sqrt(sum);
    }

    private static double manhattanDistance(double[] x, double[] y) {
        double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            sum += Math.abs(x[i] - y[i]);
        }
        return sum;
    }

    private static double cosineDistance(double[] x, double[] y) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < x.length; i++) {
            dotProduct += x[i] * y[i];
            normA += Math.pow(x[i], 2);
            normB += Math.pow(y[i], 2);
        }
        return 1 - (dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)));
    }

    /**
     * Normaliza los datos utilizando la normalización Min-Max.
     *
     * @param data Matriz de datos a normalizar.
     * @return Matriz de datos normalizados.
     */
    public static double[][] normalizeData(double[][] data) {
        int numRows = data.length;
        int numCols = data[0].length;
        double[] minValues = new double[numCols];
        double[] maxValues = new double[numCols];

        // Inicializar min y max
        for (int i = 0; i < numCols; i++) {
            minValues[i] = Double.MAX_VALUE;
            maxValues[i] = Double.MIN_VALUE;
        }

        // Encontrar min y max
        for (double[] row : data) {
            for (int i = 0; i < numCols; i++) {
                if (row[i] < minValues[i]) {
                    minValues[i] = row[i];
                }
                if (row[i] > maxValues[i]) {
                    maxValues[i] = row[i];
                }
            }
        }

        // Normalizar datos
        double[][] normalizedData = new double[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (maxValues[j] - minValues[j] == 0) {
                    normalizedData[i][j] = 0;
                } else {
                    normalizedData[i][j] = (data[i][j] - minValues[j]) / (maxValues[j] - minValues[j]);
                }
            }
        }
        return normalizedData;
    }
}
