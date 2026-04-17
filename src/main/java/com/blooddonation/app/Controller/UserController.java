package com.blooddonation.app.Controller;

import com.blooddonation.app.DTO.UserCreateDTO;
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
    // Handle GET requests for user-related operations (e.g., fetching user details)
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        userService.createAdmin(userCreateDTO);
      }
      case "create-user" -> {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserCreateDTO userCreateDTO = new UserCreateDTO(firstName, lastName, email, password);
        userService.createUser(userCreateDTO);
      }
      case "update" -> {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Handle user update logic
      }
      case "delete" -> {
        String userId = request.getParameter("userId");
        // Handle user deletion logic
      }
      default -> {
        // Handle unknown action
      }
    }
  }
}
