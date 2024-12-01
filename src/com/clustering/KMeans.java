package com.clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Implementa el algoritmo K-Means.
 */
public class KMeans implements ClusteringAlgorithm {
    private int k;
    private List<Cluster> clusters;
    private int maxIterations;
    private DistanceMetric metric;

    /**
     * Constructor de KMeans.
     *
     * @param k             Número de clusters.
     * @param maxIterations Número máximo de iteraciones.
     * @param metric        Métrica de distancia a utilizar.
     */
    public KMeans(int k, int maxIterations, DistanceMetric metric) {
        this.k = k;
        this.maxIterations = maxIterations;
        this.metric = metric;
        this.clusters = new ArrayList<>();
    }

    // Getters y Setters
    public void setK(int k) {
        this.k = k;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public void setMetric(DistanceMetric metric) {
        this.metric = metric;
    }

    @Override
    public void fit(List<DataPoint> data) throws ClusteringException {
        if (data == null || data.isEmpty()) {
            throw new ClusteringException("El conjunto de datos no puede estar vacío.");
        }

        // Inicializar centroides
        initializeCentroids(data);

        // Iterar hasta convergencia o alcanzar el máximo de iteraciones
        for (int i = 0; i < maxIterations; i++) {
            // Asignar puntos a clusters
            assignPointsToClusters(data);

            // Actualizar centroides
            boolean converged = updateCentroids();

            if (converged) {
                break;
            }
        }
    }

    @Override
    public List<Cluster> getClusters() {
        for (int i = 0; i < clusters.size(); i++) {
            Cluster cluster = clusters.get(i);
            System.out.println("Cluster " + (i + 1) + ":");
            System.out.println("Número de puntos: " + cluster.getPoints().size());
            System.out.println("Centroide: " + cluster.getCentroid());
            System.out.println();
        }
        return clusters;
    }

    @Override
    public Cluster predict(DataPoint point) {
        Cluster nearestCluster = null;
        double minDistance = Double.MAX_VALUE;
        for (Cluster cluster : clusters) {
            // Obtener características numéricas
            double[] pointFeatures = point.getNumericFeatures();
            double[] centroidFeatures = cluster.getCentroid().getNumericFeatures();
            double distance = Utils.calculateDistance(pointFeatures, centroidFeatures, metric);
            if (distance < minDistance) {
                minDistance = distance;
                nearestCluster = cluster;
            }
        }
        return nearestCluster;
    }



    @Override
    public double calculateSSE() {
        double sse = 0.0;
        for (Cluster cluster : clusters) {
            for (DataPoint point : cluster.getPoints()) {
                sse += Math.pow(point.distanceTo(cluster.getCentroid(), metric), 2);
            }
        }
        return sse;
    }

    // Métodos auxiliares
    private void initializeCentroids(List<DataPoint> data) {
        Random random = new Random();
        clusters.clear();
        for (int i = 0; i < k; i++) {
            DataPoint centroid = data.get(random.nextInt(data.size()));
            clusters.add(new Cluster(centroid));
        }
    }

    private void assignPointsToClusters(List<DataPoint> data) {
        for (Cluster cluster : clusters) {
            cluster.clearPoints();
        }
        for (DataPoint point : data) {
            Cluster nearestCluster = predict(point);
            nearestCluster.addPoint(point);
        }
    }

    private boolean updateCentroids() {
        boolean converged = true;
        for (Cluster cluster : clusters) {
            DataPoint oldCentroid = cluster.getCentroid();
            DataPoint newCentroid = calculateCentroid(cluster.getPoints());
            cluster.setCentroid(newCentroid);
            if (!Arrays.equals(oldCentroid.getFeatures(), newCentroid.getFeatures())) {
                converged = false;
            }
        }
        return converged;
    }

    private DataPoint calculateCentroid(List<DataPoint> points) {
        int dimensions = points.get(0).getNumericFeatures().length;
        double[] centroidFeatures = new double[dimensions];
        for (DataPoint point : points) {
            double[] features = point.getNumericFeatures();
            for (int i = 0; i < dimensions; i++) {
                centroidFeatures[i] += features[i];
            }
        }
        for (int i = 0; i < dimensions; i++) {
            centroidFeatures[i] /= points.size();
        }
        // Convertir double[] a Object[]
        Object[] centroidFeaturesObject = Arrays.stream(centroidFeatures).boxed().toArray();
        return new DataPoint(centroidFeaturesObject);
    }

}
