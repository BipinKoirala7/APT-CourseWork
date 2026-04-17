package com.blooddonation.app.Services;

import com.blooddonation.app.DAO.UserDAO;
import com.blooddonation.app.DTO.UserCreateDTO;
import com.blooddonation.app.DTO.UserUpdateDTO;
import com.blooddonation.app.Model.Role;
import com.blooddonation.app.Model.User;
import com.blooddonation.app.Security.PasswordHash;
import com.blooddonation.app.Security.Validator;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class UserService {

  private final UserDAO userDAO;
  private final PasswordHash passwordHash;
  private final Validator validator;

  public UserService() {
    this.userDAO = new UserDAO();
    this.passwordHash = new PasswordHash();
    this.validator = new Validator();
  }

  public void registerUser(UserCreateDTO userCreateDTO, Role role) {
    if (Objects.isNull(userCreateDTO)) throw new IllegalArgumentException("UserCreateDTO cannot be null");
    if (Objects.isNull(role)) throw new IllegalArgumentException("Role cannot be null");
    validator.validate(userCreateDTO);

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

  public void loginUser() {

  }

  public User getUser(String userId) {
    if (Objects.isNull(userId)) throw new IllegalArgumentException("User ID cannot be null");
    return userDAO.getUser(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
  }

  public void updateUser(UserUpdateDTO userUpdateDTO) {
    if (Objects.isNull(userUpdateDTO)) throw new IllegalArgumentException("UserUpdateDTO cannot be null");
    validator.validate(userUpdateDTO);

    User user = getUser(String.valueOf(userUpdateDTO.getId())); // If possible get the user id from session
    user.setFirstName(userUpdateDTO.getFirst_name());
    user.setLastName(userUpdateDTO.getLast_name());
    user.setEmail(userUpdateDTO.getEmail());
    user.setPassword(passwordHash.hashPassword(userUpdateDTO.getPassword()));

    userDAO.updateUser(user);
  }

  public void deleteUser(String userId) {
    /*
     * We need to log out the user after the user is deleted successfully
     * We can do this by invalidating the user session after the user is deleted successfully
     * We need to get the user id from session first and then proceed rather than excepting user id from client.
     * */
    if (Objects.isNull(userId)) throw new IllegalArgumentException("User ID cannot be null");
    userDAO.deleteUser(userId);
  }
}
