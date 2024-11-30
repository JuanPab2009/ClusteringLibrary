package com.clustering;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Representa un punto de datos en un espacio n-dimensional.
 * Soporta datos numéricos y categóricos, y maneja datos ausentes.
 */
public class DataPoint implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private Object[] features;
    private boolean[] missingValues;
    private String label;

    /**
     * Constructor para crear un DataPoint con características.
     *
     * @param features Array de características del punto de datos (numéricas o categóricas).
     */
    public DataPoint(Object[] features) {
        this(features, null);
    }

    /**
     * Constructor para crear un DataPoint con características y etiqueta.
     *
     * @param features Array de características del punto de datos.
     * @param label    Etiqueta opcional del punto de datos.
     */
    public DataPoint(Object[] features, String label) {
        this.features = features.clone();
        this.label = label;
        this.missingValues = new boolean[features.length];
        for (int i = 0; i < features.length; i++) {
            this.missingValues[i] = (features[i] == null);
        }
    }

    /**
     * Verifica si el punto de datos tiene valores ausentes.
     *
     * @return true si hay valores ausentes, false en caso contrario.
     */
    public boolean hasMissingValues() {
        for (boolean missing : missingValues) {
            if (missing) {
                return true;
            }
        }
        return false;
    }

    /**
     * Maneja los valores ausentes mediante una estrategia especificada.
     *
     * @param strategy  Estrategia para manejar valores ausentes ("mean", "median", "mode", "constant").
     * @param fillValue Valor constante si se usa la estrategia "constant".
     */
    public void handleMissingValues(String strategy, Double fillValue) {
        // Implementación de estrategias de imputación
        // Este método puede ser expandido según las necesidades
    }

    /**
     * Calcula la distancia a otro punto de datos utilizando una métrica especificada.
     * Solo se consideran las características numéricas.
     *
     * @param other  Otro DataPoint para calcular la distancia.
     * @param metric Métrica de distancia a utilizar.
     * @return Distancia calculada.
     */
    public double distanceTo(DataPoint other, DistanceMetric metric) {
        double[] numericFeatures1 = getNumericFeatures();
        double[] numericFeatures2 = other.getNumericFeatures();
        return Utils.calculateDistance(numericFeatures1, numericFeatures2, metric);
    }

    /**
     * Obtiene las características numéricas del DataPoint.
     *
     * @return Array de características numéricas.
     */
    public double[] getNumericFeatures() {
        return Arrays.stream(features)
                .filter(f -> f instanceof Number)
                .mapToDouble(f -> ((Number) f).doubleValue())
                .toArray();
    }

    // Getters y Setters
    public Object[] getFeatures() {
        return features.clone();
    }

    public void setFeatures(Object[] features) {
        this.features = features.clone();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    // Implementación de equals, hashCode y clone
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DataPoint)) return false;
        DataPoint other = (DataPoint) obj;
        return Arrays.equals(this.features, other.features) && Objects.equals(this.label, other.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(features), label);
    }

    @Override
    public DataPoint clone() {
        try {
            DataPoint cloned = (DataPoint) super.clone();
            cloned.features = this.features.clone();
            cloned.missingValues = this.missingValues.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * Sobrescribe el método toString para una representación legible del punto de datos.
     *
     * @return Representación en cadena del DataPoint.
     */
    @Override
    public String toString() {
        return Arrays.toString(features);
    }
}
