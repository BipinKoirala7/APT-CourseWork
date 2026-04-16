package com.blooddonation.app.DAO;

import com.blooddonation.app.Config.DBConfig;
import com.blooddonation.app.Model.Inventory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class InventoryDAO {

  private Connection connection;
  private boolean isConnectionError;

  public InventoryDAO() {
    try {
      connection = DBConfig.getConnection();
    } catch (SQLException | ClassNotFoundException e) {
      isConnectionError = false;
      System.out.println(e.getMessage());
      // Handle error here.
    }
  }

  public boolean addToInventory(Inventory inventory) {
    if (Objects.isNull(inventory)) {
      System.out.println("Inventory cannot be null");
      return false;
    }

    String query = "INSERT INTO inventory (id, donorId, units, bloodType, expiry_date, created_at) VALUES (?, ?, ?, ?, ?, ?)";

    return true;
  }
}
