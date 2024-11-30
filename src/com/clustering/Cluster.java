package com.clustering;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa un grupo de puntos de datos.
 */
public class Cluster {
    private List<DataPoint> points;
    private DataPoint centroid;

    /**
     * Constructor que inicializa el cluster sin centroid.
     */
    public Cluster() {
        this.points = new ArrayList<>();
    }

    /**
     * Constructor que inicializa el cluster con un centroid.
     *
     * @param centroid Centroid inicial del cluster.
     */
    public Cluster(DataPoint centroid) {
        this.centroid = centroid.clone();
        this.points = new ArrayList<>();
    }

    // Getters y Setters
    public List<DataPoint> getPoints() {
        return points;
    }

    public void setPoints(List<DataPoint> points) {
        this.points = points;
    }

    public DataPoint getCentroid() {
        return centroid;
    }

    public void setCentroid(DataPoint centroid) {
        this.centroid = centroid.clone();
    }

    // Métodos para manipular puntos
    public void addPoint(DataPoint point) {
        points.add(point);
    }

    public void removePoint(DataPoint point) {
        points.remove(point);
    }

    public void clearPoints() {
        points.clear();
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "centroid=" + centroid +
                ", points=" + points +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(centroid, points);
    }
}
