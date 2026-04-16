package com.blooddonation.app.DAO;

import com.blooddonation.app.Config.DBConfig;
import com.blooddonation.app.Model.BloodType;
import com.blooddonation.app.Model.Inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, String.valueOf(inventory.getId()));
      ps.setString(2, String.valueOf(inventory.getDonorId()));
      ps.setInt(3, inventory.getUnits());
      ps.setString(4, inventory.getBloodType().name());
      ps.setObject(5, inventory.getExpiryDate());
      ps.setObject(6, inventory.getCreatedAt());

      int rowsAffected = ps.executeUpdate();

      if (rowsAffected == 0) {
        System.out.println("Insert Failed");
        return false;
      }

      System.out.println("Insert Successfully");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return false;
    }

    return true;
  }

  public ArrayList<Inventory> getAllInventory() {
    String query = "SELECT * FROM inventory";
    ArrayList<Inventory> inventoryList = new ArrayList<>();

    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Inventory inventory = new Inventory();
        inventory.setId(rs.getObject("id", java.util.UUID.class));
        inventory.setDonorId(rs.getObject("donor_id", java.util.UUID.class));
        inventory.setUnits(rs.getInt("units"));
        inventory.setBloodType(BloodType.valueOf(rs.getString("blood_type")));
        inventory.setExpiryDate(rs.getObject("expiry_date", java.time.LocalDateTime.class));
        inventory.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));

        inventoryList.add(inventory);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return null;
    }
    return inventoryList;
  }

  public boolean deleteInventory(String userId) {
    String query = "DELETE FROM inventory WHERE id = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, userId);
      int rowsAffected = ps.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return false;
    }
  }
}
