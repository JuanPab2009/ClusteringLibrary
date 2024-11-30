package com.clustering;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementa el Clustering Jerárquico (Single-Link y Complete-Link).
 */
public class HierarchicalClustering implements ClusteringAlgorithm {
    private List<Cluster> clusters;
    private DistanceMetric metric;
    private LinkageType linkageType;
    private int desiredClusterCount;

    /**
     * Tipos de enlace para el clustering jerárquico.
     */
    public enum LinkageType {
        SINGLE,
        COMPLETE
    }

    /**
     * Constructor de HierarchicalClustering.
     *
     * @param metric             Métrica de distancia a utilizar.
     * @param linkageType        Tipo de enlace (SINGLE o COMPLETE).
     * @param desiredClusterCount Número deseado de clusters.
     */
    public HierarchicalClustering(DistanceMetric metric, LinkageType linkageType, int desiredClusterCount) {
        this.metric = metric;
        this.linkageType = linkageType;
        this.desiredClusterCount = desiredClusterCount;
        this.clusters = new ArrayList<>();
    }

    @Override
    public void fit(List<DataPoint> data) throws ClusteringException {
        if (data == null || data.isEmpty()) {
            throw new ClusteringException("El conjunto de datos no puede estar vacío.");
        }

        // Inicialización: cada punto es un cluster
        clusters.clear();
        for (DataPoint point : data) {
            Cluster cluster = new Cluster();
            cluster.addPoint(point);
            clusters.add(cluster);
        }

        // Iteración hasta obtener el número deseado de clusters
        while (clusters.size() > desiredClusterCount) {
            // Encontrar los clusters más cercanos
            double minDistance = Double.MAX_VALUE;
            int clusterIndexA = -1;
            int clusterIndexB = -1;

            for (int i = 0; i < clusters.size(); i++) {
                for (int j = i + 1; j < clusters.size(); j++) {
                    double distance = calculateDistanceBetweenClusters(clusters.get(i), clusters.get(j));
                    if (distance < minDistance) {
                        minDistance = distance;
                        clusterIndexA = i;
                        clusterIndexB = j;
                    }
                }
            }

            // Combinar clusters
            mergeClusters(clusterIndexA, clusterIndexB);
        }
    }

    @Override
    public List<Cluster> getClusters() {
        return clusters;
    }

    @Override
    public Cluster predict(DataPoint point) {
        // No es común predecir nuevos puntos en clustering jerárquico estático
        return null;
    }

    @Override
    public double calculateSSE() {
        // No es aplicable en clustering jerárquico estándar
        return 0;
    }

    // Métodos auxiliares
    private double calculateDistanceBetweenClusters(Cluster clusterA, Cluster clusterB) {
        double distance;
        if (linkageType == LinkageType.SINGLE) {
            distance = Double.MAX_VALUE;
            for (DataPoint pointA : clusterA.getPoints()) {
                for (DataPoint pointB : clusterB.getPoints()) {
                    double currentDistance = pointA.distanceTo(pointB, metric);
                    if (currentDistance < distance) {
                        distance = currentDistance;
                    }
                }
            }
        } else {
            distance = Double.MIN_VALUE;
            for (DataPoint pointA : clusterA.getPoints()) {
                for (DataPoint pointB : clusterB.getPoints()) {
                    double currentDistance = pointA.distanceTo(pointB, metric);
                    if (currentDistance > distance) {
                        distance = currentDistance;
                    }
                }
            }
        }
        return distance;
    }

    private void mergeClusters(int indexA, int indexB) {
        Cluster clusterA = clusters.get(indexA);
        Cluster clusterB = clusters.get(indexB);

        // Combinar puntos
        clusterA.getPoints().addAll(clusterB.getPoints());

        // Remover el clusterB
        clusters.remove(indexB);
    }
}
