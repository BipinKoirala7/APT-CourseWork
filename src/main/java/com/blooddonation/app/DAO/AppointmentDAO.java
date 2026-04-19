package com.blooddonation.app.DAO;

import com.blooddonation.app.Config.DBConfig;
import com.blooddonation.app.Exception.DatabaseConnection;
import com.blooddonation.app.Exception.QueryExecutionException;
import com.blooddonation.app.Model.Appointment;
import com.blooddonation.app.Model.AppointmentStatus;
import com.blooddonation.app.Model.AppointmentType;
import com.blooddonation.app.Model.BloodType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AppointmentDAO {

  private Connection connection;
  private boolean isConnectionError;

  public AppointmentDAO() {
    try {
      connection = DBConfig.getConnection();
      isConnectionError = false;
    } catch (SQLException | ClassNotFoundException e) {
      isConnectionError = true;
      throw new DatabaseConnection("Database Connection Error: " + e.getMessage());
    }
  }

  public void createAppointment(Appointment appointment) {
    if (isConnectionError) throw new DatabaseConnection("Database Connection Error");
    if (appointment == null) throw new IllegalArgumentException("Appointment cannot be null");

    String query = "INSERT INTO appointment (id, adminId, userId, type, blood_type, units, time, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, String.valueOf(appointment.getId()));
      ps.setString(2, null);
      ps.setString(3, String.valueOf(appointment.getUserId()));
      ps.setString(4, appointment.getType().name());
      ps.setString(5, appointment.getBloodType().name());
      ps.setInt(6, appointment.getUnits());
      ps.setObject(7, appointment.getTime());
      ps.setString(8, appointment.getStatus().name());
      ps.setObject(9, appointment.getCreatedAt());
      ps.setObject(10, appointment.getUpdatedAt());

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected == 0) throw new QueryExecutionException("Appointment does not exist");

      System.out.println("Insert Successfully");
    } catch (SQLException e) {
      throw new DatabaseConnection("Database Connection Error: " + e.getMessage());
    }
  }

  public ArrayList<Appointment> getAllAppointmentsByUserId(String userId) {
    if (isConnectionError) throw new DatabaseConnection("Database Connection Error");
    if (userId == null || userId.isBlank()) throw new IllegalArgumentException("userId cannot be null");

    ArrayList<Appointment> appointments = new ArrayList<>();
    String query = "SELECT * FROM appointment where userId = ?";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, userId);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Appointment appointment = new Appointment(
            UUID.fromString(rs.getString("id")),
            rs.getString("adminId") != null ? UUID.fromString(rs.getString("adminId")) : null,
            UUID.fromString(rs.getString("userId")),
            AppointmentType.valueOf(rs.getString("type")),
            BloodType.valueOf(rs.getString("blood_type")),
            rs.getInt("units"),
            rs.getObject("time", Instant.class),
            AppointmentStatus.valueOf(rs.getString("status")),
            rs.getObject("created_at", Instant.class),
            rs.getObject("updated_at", Instant.class)
        );

        appointments.add(appointment);
      }
      System.out.println("Fetch Successfully");
    } catch (SQLException e) {
      throw new DatabaseConnection("Database Connection Error: " + e.getMessage());
    }
    return appointments;
  }

  //  Strictly for accepting appointment by admin
  public void updateAppointmentStatus(String id, String adminId, AppointmentStatus status) {
    if (isConnectionError) throw new DatabaseConnection("Database Connection Error");
    if (id == null || id.isBlank()) throw new IllegalArgumentException("Appointment ID cannot be null or blank");
    if (adminId == null || adminId.isBlank()) throw new IllegalArgumentException("Admin ID cannot be null or blank");
    if (status == null) throw new IllegalArgumentException("Status cannot be null");

    String query = "UPDATE appointments SET adminId = ?, status = ?, updated_at = ? WHERE id = ?";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, adminId);
      ps.setString(2, status.name());
      ps.setObject(3, Instant.now());
      ps.setString(4, id);

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected == 0) throw new QueryExecutionException("Failed to update appointment status");

      System.out.println("Status Update Successfully");
    } catch (SQLException e) {
      throw new DatabaseConnection("Database Connection Error: " + e.getMessage());
    }
  }

  public void updateAppointmentStatus(String id, AppointmentStatus status) {
    if (isConnectionError) throw new DatabaseConnection("Database Connection Error");
    if (id == null || id.isBlank()) throw new IllegalArgumentException("Appointment ID cannot be null or blank");
    if (status == null) throw new IllegalArgumentException("Status cannot be null");

    String query = "UPDATE appointments SET status = ?, updated_at = ? WHERE id = ?";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, status.name());
      ps.setObject(2, Instant.now());
      ps.setString(3, id);

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected == 0) throw new QueryExecutionException("Failed to update appointment status");

      System.out.println("Status Update Successfully");
    } catch (SQLException e) {
      throw new DatabaseConnection("Database Connection Error: " + e.getMessage());
    }
  }

  public void updateAppointment(Appointment updatedAppointment) {
    if (isConnectionError) throw new DatabaseConnection("Database Connection Error");
    if (updatedAppointment == null) throw new IllegalArgumentException("Appointment cannot be null");

    String query = "UPDATE appointments SET adminId = ?, userId = ?, type = ?, blood_type = ?, units = ?, time = ?, status = ?, created_at = ?, updated_at = ? WHERE id = ?";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, String.valueOf(updatedAppointment.getAdminId()));
      ps.setString(2, String.valueOf(updatedAppointment.getUserId()));
      ps.setString(3, updatedAppointment.getType().name());
      ps.setString(4, updatedAppointment.getBloodType().name());
      ps.setInt(5, updatedAppointment.getUnits());
      ps.setObject(6, updatedAppointment.getTime());
      ps.setString(7, updatedAppointment.getStatus().name());
      ps.setObject(8, updatedAppointment.getCreatedAt());
      ps.setObject(9, updatedAppointment.getUpdatedAt());
      ps.setString(10, String.valueOf(updatedAppointment.getId()));

      int rowsAffected = ps.executeUpdate();

      if (rowsAffected == 0) throw new QueryExecutionException("Failed to update appointment");

      System.out.println("Update Successfully");
    } catch (SQLException e) {
      throw new DatabaseConnection("Database Connection Error: " + e.getMessage());
    }
  }

  public void deleteAppointment(String id) {
    if (isConnectionError) throw new DatabaseConnection("Database Connection Error");
    if (id == null || id.isBlank())
      throw new IllegalArgumentException("Appointment ID cannot be null or blank");

    String query = "DELETE FROM appointments WHERE id = ?";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, id);

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected == 0) throw new QueryExecutionException("Failed to delete appointment from database");

      System.out.println("Delete Successfully");
    } catch (SQLException e) {
      throw new DatabaseConnection("Database Connection Error: " + e.getMessage());
    }
  }
}
