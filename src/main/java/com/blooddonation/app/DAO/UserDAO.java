package com.blooddonation.app.DAO;

import com.blooddonation.app.Config.DBConfig;
import com.blooddonation.app.Exception.DatabaseConnection;
import com.blooddonation.app.Exception.QueryExecutionException;
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

  public void createUser(User user) {
    if (isConnectionError) throw new DatabaseConnection("Database Connection Error");
    if (Objects.isNull(user)) throw new IllegalArgumentException("User cannot be null");

    String query = "INSERT INTO users (id, first_name, last_name, email, password, created_at) VALUES (?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, String.valueOf(user.getId()));
      ps.setString(2, user.getFirstName());
      ps.setString(3, user.getLastName());
      ps.setString(4, user.getEmail());
      ps.setString(5, user.getPassword());
      ps.setObject(6, user.getCreatedAt());

      int rowsAffected = ps.executeUpdate();

      if (rowsAffected == 0) throw new QueryExecutionException("Failed to insert user into database");

      System.out.println("Insert Successfully");
    } catch (SQLException e) {
      throw new DatabaseConnection("Database Connection Error: " + e.getMessage());
    }
  }

  public Optional<User> getUser(String userId) {
    if (isConnectionError) throw new DatabaseConnection("Database Connection Error");
    if (Objects.isNull(userId)) throw new IllegalArgumentException("User ID cannot be null");

    String query = "SELECT * FRrIOM users where id=?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, userId);

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
    } catch (SQLException e) {
      throw new DatabaseConnection("Database Connection Error: " + e.getMessage());
    }
  }

  public Optional<User> getUserByEmail(String email) {
    if (isConnectionError) throw new DatabaseConnection("Database Connection Error");
    if (Objects.isNull(email)) throw new IllegalArgumentException("Email cannot be null");

    String query = "SELECT * FROM users WHERE email=?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, email);

      ResultSet rs = ps.executeQuery();
      if (!rs.next()) {
        System.out.println("User with given email doesn't exists");
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
    } catch (SQLException e) {
      throw new DatabaseConnection("Database Connection Error: " + e.getMessage());
    }
  }

  public void updateUser(User updatedUser) {
    if (isConnectionError) throw new DatabaseConnection("Database Connection Error");
    if (Objects.isNull(updatedUser)) throw new IllegalArgumentException("Updated user cannot be null");

    String query = "UPDATE users SET first_name=?, last_name=?, email=?, password=? WHERE id=?";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, updatedUser.getFirstName());
      ps.setString(2, updatedUser.getLastName());
      ps.setString(3, updatedUser.getEmail());
      ps.setString(4, updatedUser.getPassword());
      ps.setString(5, String.valueOf(updatedUser.getId()));

      int rowsAffected = ps.executeUpdate();
      if (rowsAffected == 0) throw new QueryExecutionException("Failed to update user in database");

      System.out.println("Update Successfully");
    } catch (SQLException e) {
      throw new DatabaseConnection("Database Connection Error: " + e.getMessage());
    }
  }

  public void deleteUser(String userId) {
    if (isConnectionError) throw new DatabaseConnection("Database Connection Error");
    if (Objects.isNull(userId)) throw new IllegalArgumentException("User ID cannot be null");

    String query = "DELETE FROM users WHERE id=?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, userId);
      int rowsAffected = ps.executeUpdate();

      if (rowsAffected == 0) throw new QueryExecutionException("Failed to delete user from database");

      System.out.println("Delete Successfully");
    } catch (SQLException e) {
      throw new DatabaseConnection("Database Connection Error: " + e.getMessage());
    }
  }
}
