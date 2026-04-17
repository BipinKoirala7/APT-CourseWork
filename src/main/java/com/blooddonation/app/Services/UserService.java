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

  private final String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
  private final UserDAO userDAO;
  private final PasswordHash passwordHash;

  public UserService() {
    this.userDAO = new UserDAO();
    this.passwordHash = new PasswordHash();
  }

  public void registerUser(UserCreateDTO userCreateDTO, Role role) {
    if (Objects.isNull(userCreateDTO)) throw new IllegalArgumentException("UserCreateDTO cannot be null");
    if (Objects.isNull(role)) throw new IllegalArgumentException("Role cannot be null");
    if (userCreateDTO.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
      throw new IllegalArgumentException("Proper email is required");
    if (userCreateDTO.getPassword().matches(passwordRegex))
      throw new IllegalArgumentException("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character");

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
