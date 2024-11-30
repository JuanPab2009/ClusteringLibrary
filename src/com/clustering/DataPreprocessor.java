package com.clustering;

import java.util.*;

/**
 * Clase para preprocesamiento de datos, incluyendo imputación y codificación.
 */
public class DataPreprocessor {

    /**
     * Imputa los valores ausentes en el conjunto de datos utilizando la media de cada característica.
     *
     * @param data Lista de DataPoint.
     */
    public static void imputeMissingValues(List<DataPoint> data) {
        // Implementación similar a la anterior, manejando características numéricas
    }

    /**
     * Realiza One-Hot Encoding de las características categóricas en los DataPoints.
     *
     * @param data Lista de DataPoint.
     */
    public static void oneHotEncodeFeatures(List<DataPoint> data) {
        // Identificar las características categóricas
        int numFeatures = data.get(0).getFeatures().length;
        List<Integer> categoricalIndices = new ArrayList<>();
        for (int i = 0; i < numFeatures; i++) {
            Object feature = data.get(0).getFeatures()[i];
            if (!(feature instanceof Number)) {
                categoricalIndices.add(i);
            }
        }

        // Mapear categorías a índices
        Map<Integer, Map<Object, Integer>> categoryMaps = new HashMap<>();
        for (int index : categoricalIndices) {
            Map<Object, Integer> categoryMap = new HashMap<>();
            int categoryIndex = 0;
            for (DataPoint dp : data) {
                Object category = dp.getFeatures()[index];
                if (!categoryMap.containsKey(category)) {
                    categoryMap.put(category, categoryIndex++);
                }
            }
            categoryMaps.put(index, categoryMap);
        }

        // Crear nuevos DataPoints con características codificadas
        List<DataPoint> newData = new ArrayList<>();
        for (DataPoint dp : data) {
            List<Object> newFeatures = new ArrayList<>();
            Object[] features = dp.getFeatures();
            for (int i = 0; i < features.length; i++) {
                if (categoricalIndices.contains(i)) {
                    Map<Object, Integer> categoryMap = categoryMaps.get(i);
                    int numCategories = categoryMap.size();
                    Integer categoryIndex = categoryMap.get(features[i]);
                    // One-Hot Encoding
                    for (int j = 0; j < numCategories; j++) {
                        newFeatures.add(j == categoryIndex ? 1.0 : 0.0);
                    }
                } else {
                    newFeatures.add(features[i]);
                }
            }
            dp.setFeatures(newFeatures.toArray());
            newData.add(dp);
        }
    }
}
