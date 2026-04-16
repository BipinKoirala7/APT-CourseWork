package com.blooddonation.app.DAO;

import com.blooddonation.app.Config.DBConfig;
import com.blooddonation.app.Model.User;

import java.sql.*;
import java.util.Objects;
import java.util.UUID;

public class UserDAO {

  private Connection connection;
  private boolean isConnectionError;

  public UserDAO(){
    try{
      connection = DBConfig.getConnection();
    } catch (SQLException | ClassNotFoundException e){
      isConnectionError = false;
      System.out.println(e.getMessage());
      // Handle error here.
    }
  }

  public boolean createUser(User user) throws SQLException {
    if(Objects.isNull(user)){
      System.out.println("User cannot be null");
      return false;
    }
    String query = "INSERT INTO users (id, first_name, last_name, email, password, created_at) VALUES (?, ?, ?, ?, ?, ?)";
    try(PreparedStatement ps = connection.prepareStatement(query)){
      ps.setString(1, String.valueOf(user.getId()));
      ps.setString(2, user.getFirst_name());
      ps.setString(3, user.getLast_name());
      ps.setString(4, user.getEmail());
      ps.setString(5, user.getPassword());
      ps.setObject(6, user.getCreated_at());

      int rowsAffected = ps.executeUpdate();

      if(rowsAffected == 0){
        System.out.println("Insert Failed");
        return false;
      }

      System.out.println("Inset Successfully");
      return true;
    }
  }

  public User getUser(String userId) throws SQLException {
    if(Objects.isNull(userId)){
      System.out.println("User ID cannot be null");
      return null;
    }

    String query = "SELECT * FROM users where id=?";
    try(PreparedStatement ps = connection.prepareStatement(query)){
      ps.setString(1, userId);

      ResultSet rs = ps.executeQuery();
      if(!rs.next()){
        System.out.println("User with given id doesn't exists");
        return null;
      }

      User user = new User();
      user.setId(UUID.fromString(rs.getString("id")));
      user.setFirst_name(rs.getString("first_name"));
      user.setLast_name(rs.getString("last_name"));
      user.setEmail(rs.getString("email"));
      user.setPassword("password");
      user.setCreated_at(rs.getTimestamp("created_at").toInstant());

      return user;
    }
  }

  public boolean updateUser(User updatedUser) throws SQLException {
    if(Objects.isNull(updatedUser)){
      System.out.println("User cannot be null");
      return false;
    }

    String query = "UPDATE users SET first_name=?, last_name=?, email=?, password=? WHERE user_id=?";

    try(PreparedStatement ps = connection.prepareStatement(query)){
      ps.setString(1, updatedUser.getFirst_name());
      ps.setString(2, updatedUser.getLast_name());
      ps.setString(3, updatedUser.getEmail());
      ps.setString(4, updatedUser.getPassword());
      ps.setString(5, String.valueOf(updatedUser.getId()));

      int rowsAffected = ps.executeUpdate();
      if(rowsAffected == 0){
        System.out.println("Update Failed");
        return false;
      }

      System.out.println("Inset Successfully");
      return true;
    }
  }

  public boolean deleteUser(String  userId) throws SQLException {
    if(Objects.isNull(userId)){
      System.out.println("User ID cannot be null");
      return false;
    }

    String query = "DELETE FROM users WHERE user_id=?";
    try(PreparedStatement ps = connection.prepareStatement(query)){
      ps.setString(1, userId);

      int rowsAffected = ps.executeUpdate();
      if(rowsAffected == 0){
        System.out.println("Delete Failed");
        return false;
      }

      System.out.println("Delete Successfully");
      return true;
    }
  }
}
