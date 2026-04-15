package com.blooddonation.app.DAO;

import com.blooddonation.app.Config.DBConfig;

import java.sql.Connection;
import java.sql.SQLException;

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

  public void createUser(){

  }

  public void getUser(){

  }

  public void updateUser(){}

  public void deleteUser(){

  }
}
