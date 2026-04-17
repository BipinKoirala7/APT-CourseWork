package com.blooddonation.app.Services;

import com.blooddonation.app.DAO.UserDAO;
import com.blooddonation.app.DTO.UserCreateDTO;
import com.blooddonation.app.Model.Role;
import com.blooddonation.app.Model.User;
import com.blooddonation.app.Security.PasswordHash;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class UserService {

  private final UserDAO userDAO;
  private final PasswordHash passwordHash;

  public UserService() {
    this.userDAO = new UserDAO();
    this.passwordHash = new PasswordHash();
  }

  public void registerUser(UserCreateDTO userCreateDTO, Role role) {
    if (Objects.isNull(userCreateDTO)) {
      System.out.println("UserCreateDTO cannot be null");
      return;
    }

    User user = new User(
        UUID.randomUUID(),
        userCreateDTO.getFirst_name(),
        userCreateDTO.getLast_name(),
        role,
        userCreateDTO.getEmail(),
        passwordHash.hashPassword(userCreateDTO.getPassword()),
        Instant.now()
    );

    userDAO.createUser(user);
  }
}
