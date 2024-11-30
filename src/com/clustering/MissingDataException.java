package com.clustering;

/**
 * Excepción para manejar datos ausentes.
 */
public class MissingDataException extends Exception {
  public MissingDataException(String message) {
    super(message);
  }

  public MissingDataException(String message, Throwable cause) {
    super(message, cause);
  }
}
