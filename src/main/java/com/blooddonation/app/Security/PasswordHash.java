package com.blooddonation.app.Security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash {
  private final int salt = 12;

  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt(salt));
  }

  public boolean checkIfPasswordsMatch(String hashPassword, String typedPassword) {
    return BCrypt.checkpw(hashPassword, typedPassword);
  }
}
