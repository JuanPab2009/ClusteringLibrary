package com.clustering;

import java.util.List;

/**
 * Interfaz que define los métodos para los algoritmos de clustering.
 */
public interface ClusteringAlgorithm {

    /**
     * Entrena el modelo con los datos proporcionados.
     *
     * @param data Lista de puntos de datos.
     * @throws ClusteringException Si ocurre un error durante el entrenamiento.
     */
    void fit(List<DataPoint> data) throws ClusteringException;

    /**
     * Devuelve los clusters generados por el algoritmo.
     *
     * @return Lista de clusters.
     */
    List<Cluster> getClusters();

    /**
     * Predice el cluster para un nuevo punto de datos.
     *
     * @param point Punto de datos a predecir.
     * @return Cluster asignado.
     */
    Cluster predict(DataPoint point);

    /**
     * Calcula el SSE (Sum of Squared Errors) del modelo si aplica.
     *
     * @return Valor del SSE.
     */
    double calculateSSE();
}
