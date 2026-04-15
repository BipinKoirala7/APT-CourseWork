package com.blooddonation.app.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
  private static final String DB_NAME = "blood_donation";
  private static final String DB_USER = "postgres";
  private static final String DB_PASSWORD = "postgres";
  private static final String url = "jdbc:postgresql://localhost:5432/" + DB_NAME;

  private static Connection getConnection() throws ClassNotFoundException, SQLException {
    Class.forName("org.postgresql.jdbc");
    return DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
  }

}
