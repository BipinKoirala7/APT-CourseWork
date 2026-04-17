package com.blooddonation.app.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginDTO {

  @NotNull(message = "Email cannot be null")
  @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters")
  @Email(message = "Email must be a valid email address")
  private String email;

  @NotNull(message = "Password cannot be null")
  @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
  private String password;
}
