package com.blooddonation.app.Controller;

import com.blooddonation.app.DTO.UserCreateDTO;
import com.blooddonation.app.DTO.UserLoginDTO;
import com.blooddonation.app.DTO.UserUpdateDTO;
import com.blooddonation.app.Model.Role;
import com.blooddonation.app.Model.User;
import com.blooddonation.app.Services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UserController", urlPatterns = {"/user"})
public class UserController extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      UserService userService = new UserService();
      request.setCharacterEncoding("UTF-8");
      String action = request.getParameter("action");

      User user = userService.getUser(request.getParameter("userId")); // try to get this from session for better security
    } catch (RuntimeException e) {
      e.printStackTrace();
      // Handle exceptions (e.g., log the error, send an error response)
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      UserService userService = new UserService();
      request.setCharacterEncoding("UTF-8");
      String action = request.getParameter("action");

      switch (action) {
        case "create-admin" -> {
          String firstName = request.getParameter("first_name");
          String lastName = request.getParameter("last_name");
          String email = request.getParameter("email");
          String password = request.getParameter("password");

          UserCreateDTO userCreateDTO = new UserCreateDTO(firstName, lastName, email, password);
          userService.registerUser(userCreateDTO, Role.ADMIN);
        }
        case "create-user" -> {
          String firstName = request.getParameter("first_name");
          String lastName = request.getParameter("last_name");
          String email = request.getParameter("email");
          String password = request.getParameter("password");

          UserCreateDTO userCreateDTO = new UserCreateDTO(firstName, lastName, email, password);
          userService.registerUser(userCreateDTO, Role.USER);
        }
        case "login-user" -> {
          String email = request.getParameter("email");
          String password = request.getParameter("password");

          UserLoginDTO userLoginDTO = new UserLoginDTO(email, password);
          userService.loginUser(userLoginDTO);
        }
        case "login-admin" -> {
          String email = request.getParameter("email");
          String password = request.getParameter("password");

          UserLoginDTO userLoginDTO = new UserLoginDTO(email, password);
          userService.loginUser(userLoginDTO);
        }
        case "update" -> {
          // Make it a put request rather than a post request
          String id = request.getParameter("id"); // try to get this from session for better security
          String firstName = request.getParameter("first_name");
          String lastName = request.getParameter("last_name");
          String email = request.getParameter("email");
          String password = request.getParameter("password");

          UserUpdateDTO userUpdateDTO = new UserUpdateDTO(id, firstName, lastName, email, password);
          userService.updateUser(userUpdateDTO);
        }
        case "delete" -> {
          String userId = request.getParameter("userId");
          userService.deleteUser(userId);
        }
        default -> {
          // Handle unknown action
        }
      }
    } catch (RuntimeException e) {
      e.printStackTrace();
      // Handle exceptions (e.g., log the error, send an error response)
    }
  }
}