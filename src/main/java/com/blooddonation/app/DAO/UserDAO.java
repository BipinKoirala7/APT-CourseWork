package com.blooddonation.app.DAO;

import com.blooddonation.app.Config.DBConfig;
import com.blooddonation.app.Model.Role;
import com.blooddonation.app.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserDAO {

  private Connection connection;
  private boolean isConnectionError;

  public UserDAO() {
    try {
      connection = DBConfig.getConnection();
      isConnectionError = false;
    } catch (SQLException | ClassNotFoundException e) {
      isConnectionError = true;
      System.out.println(e.getMessage());
      // Handle error here.
    }
  }

  public boolean createUser(User user) throws SQLException {
    if (isConnectionError) {
      System.out.println("Connection Error");
      return false;
    }

    if (Objects.isNull(user)) {
      System.out.println("User cannot be null");
      return false;
    }
    String query = "INSERT INTO users (id, first_name, last_name, email, password, created_at) VALUES (?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, String.valueOf(user.getId()));
      ps.setString(2, user.getFirstName());
      ps.setString(3, user.getLastName());
      ps.setString(4, user.getEmail());
      ps.setString(5, user.getPassword());
      ps.setObject(6, user.getCreatedAt());

      int rowsAffected = ps.executeUpdate();

      if (rowsAffected == 0) {
        System.out.println("Insert Failed");
        return false;
      }

      System.out.println("Insert Successfully");
      return true;
    }
  }

  public Optional<User> getUser(String userId, Role role) throws SQLException {
    if (isConnectionError) {
      System.out.println("Connection Error");
      return Optional.empty();
    }

    if (Objects.isNull(userId)) {
      System.out.println("User ID cannot be null");
      return Optional.empty();
    }

    String query = "SELECT * FROM users where id=? AND role=?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, userId);
      ps.setString(2, role.toString());

      ResultSet rs = ps.executeQuery();
      if (!rs.next()) {
        System.out.println("User with given id doesn't exists");
        return Optional.empty();
      }

      User user = new User(
          UUID.fromString(rs.getString("id")),
          rs.getString("first_name"),
          rs.getString("last_name"),
          Role.valueOf(rs.getString("role")),
          rs.getString("email"),
          "password",
          rs.getTimestamp("created_at").toInstant()
      );

      return Optional.of(user);
    }
  }

  public boolean updateUser(User updatedUser) throws SQLException {
    if (isConnectionError) {
      System.out.println("Connection Error");
      return false;
    }

    if (Objects.isNull(updatedUser)) {
      System.out.println("User cannot be null");
      return false;
    }

    String query = "UPDATE users SET first_name=?, last_name=?, email=?, password=? WHERE user_id=?";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, updatedUser.getFirstName());
      ps.setString(2, updatedUser.getLastName());
      ps.setString(3, updatedUser.getEmail());
      ps.setString(4, updatedUser.getPassword());
      ps.setString(5, String.valueOf(updatedUser.getId()));

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected == 0) {
        System.out.println("Update Failed");
        return false;
      }

      System.out.println("Inset Successfully");
      return true;
    }
  }

  public boolean deleteUser(String userId) throws SQLException {
    if (isConnectionError) {
      System.out.println("Connection Error");
      return false;
    }

    if (Objects.isNull(userId)) {
      System.out.println("User ID cannot be null");
      return false;
    }

    String query = "DELETE FROM users WHERE user_id=?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, userId);

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected == 0) {
        System.out.println("Delete Failed");
        return false;
      }

      System.out.println("Delete Successfully");
      return true;
    }
  }
}
