package com.clustering;

/**
 * Excepción personalizada para manejar errores en la librería de clustering.
 */
public class ClusteringException extends Exception {
    public ClusteringException(String message) {
        super(message);
    }

    public ClusteringException(String message, Throwable cause) {
        super(message, cause);
    }
}