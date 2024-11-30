package com.clustering;

/**
 * Patrón Singleton para crear instancias de algoritmos de clustering.
 */
public class ClusteringFactory {
    private static ClusteringFactory instance = null;

    private ClusteringFactory() {
        // Constructor privado
    }

    public static ClusteringFactory getInstance() {
        if (instance == null) {
            instance = new ClusteringFactory();
        }
        return instance;
    }

    /**
     * Crea una instancia de un algoritmo de clustering basado en el tipo especificado.
     *
     * @param type Tipo de algoritmo.
     * @return Instancia de ClusteringAlgorithm.
     */
    public ClusteringAlgorithm createAlgorithm(AlgorithmType type) {
        switch (type) {
            case KMEANS:
                return new KMeans(3, 100, DistanceMetric.EUCLIDEAN);
            case HIERARCHICAL:
                return new HierarchicalClustering(DistanceMetric.EUCLIDEAN, HierarchicalClustering.LinkageType.SINGLE, 3);
            default:
                throw new IllegalArgumentException("Tipo de algoritmo no soportado.");
        }
    }
}
