package com.blooddonation.app.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateDTO {

  @NotNull(message = "First name cannot be null")
  @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
  private String first_name;

  @NotNull(message = "Last name cannot be null")
  @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
  private String last_name;

  @NotNull(message = "Email cannot be null")
  private String email;

  @NotNull(message = "Password cannot be null")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  private String password;
}
