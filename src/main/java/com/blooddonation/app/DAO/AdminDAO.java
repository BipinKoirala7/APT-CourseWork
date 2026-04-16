package com.blooddonation.app.DAO;

import com.blooddonation.app.Config.DBConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminDAO {
  private Connection connection;
  private boolean isConnectionError;

  AdminDAO(){
    try{
      connection = DBConfig.getConnection();
    } catch (SQLException | ClassNotFoundException e){
      isConnectionError = false;
      System.out.println(e.getMessage());
      // Handle error here.
    }
  }
}
