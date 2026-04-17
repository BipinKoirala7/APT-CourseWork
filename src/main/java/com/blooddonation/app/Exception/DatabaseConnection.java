package com.blooddonation.app.Exception;

public class DatabaseConnection extends RuntimeException {
  public DatabaseConnection(String message) {
    super(message);
  }
}
