package com.clustering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Cargar datos desde un archivo CSV
            DataLoader dataLoader = new DataLoader();
            List<DataPoint> data = dataLoader.loadFromCSV("/Users/juanpabloblanco/Downloads/Clases/iris.csv");

            // Manejar datos ausentes
            DataPreprocessor.imputeMissingValues(data);

            // Codificar características categóricas
            DataPreprocessor.oneHotEncodeFeatures(data);

            // Normalizar datos
            List<double[]> numericFeaturesList = new ArrayList<>();
            for (DataPoint dp : data) {
                numericFeaturesList.add(dp.getNumericFeatures());
            }
            double[][] features = numericFeaturesList.toArray(new double[0][]);

// Normalizar usando Utils
            double[][] normalizedFeatures = Utils.normalizeData(features);

// Actualizar las características numéricas en los DataPoints
            for (int i = 0; i < data.size(); i++) {
                DataPoint dp = data.get(i);
                double[] normalizedNumericFeatures = normalizedFeatures[i];
                Object[] originalFeatures = dp.getFeatures();
                Object[] newFeatures = new Object[originalFeatures.length];

                int numericIndex = 0;
                for (int j = 0; j < originalFeatures.length; j++) {
                    if (originalFeatures[j] instanceof Number) {
                        newFeatures[j] = normalizedNumericFeatures[numericIndex++];
                    } else {
                        newFeatures[j] = originalFeatures[j];
                    }
                }
                dp.setFeatures(newFeatures);
            }

            for (int i = 0; i < data.size(); i++) {
                Object[] fullFeatures = data.get(i).getFeatures();
                double[] numericFeatures = normalizedFeatures[i];
                int numNumeric = numericFeatures.length;
                // Asumiendo que después de One-Hot Encoding, todas las características son numéricas
                data.get(i).setFeatures(Arrays.stream(numericFeatures).boxed().toArray());
            }

            // Crear instancia del algoritmo KMeans
            KMeans kmeans = new KMeans(3, 100, DistanceMetric.EUCLIDEAN);

            // Entrenar el modelo
            kmeans.fit(data);

            // Obtener los clusters
            List<Cluster> clusters = kmeans.getClusters();
            for (int i = 0; i < clusters.size(); i++) {
                Cluster cluster = clusters.get(i);
                System.out.println("Cluster " + (i + 1) + ":");
                System.out.println("Número de puntos: " + cluster.getPoints().size());
                System.out.println("Centroide: " + cluster.getCentroid());
                System.out.println();
            }

            // Calcular el SSE
            double sse = kmeans.calculateSSE();
            System.out.println("SSE: " + sse);



        } catch (IOException | ClusteringException | InvalidDataException e) {
            e.printStackTrace();
        }
    }
}
