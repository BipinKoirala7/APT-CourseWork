package com.blooddonation.app.DAO;

import com.blooddonation.app.Config.DBConfig;
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
      System.out.println(e.getMessage());
      // Handle error here.
    }
  }

  public boolean createAppointment(Appointment appointment) {
    if (isConnectionError) {
      System.out.println("Connection Error");
      return false;
    }

    if (appointment == null) {
      System.out.println("Appointment cannot be null");
      return false;
    }

    String query = "INSERT INTO appointment (id, adminId, userId, type, blood_type, units, time, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, String.valueOf(appointment.getId()));
      ps.setString(2, null);
      ps.setString(3, String.valueOf(appointment.getUserid()));
      ps.setString(4, appointment.getType().name());
      ps.setString(5, appointment.getBloodType().name());
      ps.setInt(6, appointment.getUnits());
      ps.setObject(7, appointment.getTime());
      ps.setString(8, appointment.getStatus().name());
      ps.setObject(9, appointment.getCreatedAt());
      ps.setObject(10, appointment.getUpdatedAt());

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

  public ArrayList<Appointment> getAllAppointments() {
    if (isConnectionError) {
      System.out.println("Connection Error");
      return null;
    }

    ArrayList<Appointment> appointments = new ArrayList<>();
    String query = "SELECT * FROM appointment";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
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
      System.out.println(e.getMessage());
      return null;
    }
    return appointments;
  }

  public boolean updateAppointment(Appointment updatedAppointment) {
    if (isConnectionError) {
      System.out.println("Connection Error");
      return false;
    }

    if (updatedAppointment == null) {
      System.out.println("Appointment cannot be null");
      return false;
    }

    String query = "UPDATE appointments SET adminId = ?, userId = ?, type = ?, blood_type = ?, units = ?, time = ?, status = ?, created_at = ?, updated_at = ? WHERE id = ?";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, String.valueOf(updatedAppointment.getAdminId()));
      ps.setString(2, String.valueOf(updatedAppointment.getUserid()));
      ps.setString(3, updatedAppointment.getType().name());
      ps.setString(4, updatedAppointment.getBloodType().name());
      ps.setInt(5, updatedAppointment.getUnits());
      ps.setObject(6, updatedAppointment.getTime());
      ps.setString(7, updatedAppointment.getStatus().name());
      ps.setObject(8, updatedAppointment.getCreatedAt());
      ps.setObject(9, updatedAppointment.getUpdatedAt());
      ps.setString(10, String.valueOf(updatedAppointment.getId()));

      int rowsAffected = ps.executeUpdate();

      if (rowsAffected == 0) {
        System.out.println("Update Failed");
        return false;
      }

      System.out.println("Update Successfully");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return false;
    }

    return true;
  }

  public boolean deleteAppointment(String appointmentId) {
    if (isConnectionError) {
      System.out.println("Connection Error");
      return false;
    }

    if (appointmentId == null || appointmentId.isBlank()) {
      System.out.println("Appointment ID cannot be null or empty");
      return false;
    }

    String query = "DELETE FROM appointments WHERE id = ?";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, appointmentId);

      int rowsAffected = ps.executeUpdate();

      if (rowsAffected == 0) {
        System.out.println("Delete Failed");
        return false;
      }

      System.out.println("Delete Successfully");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return false;
    }

    return true;
  }
}
