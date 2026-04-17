package com.blooddonation.app.Model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class User {

  @NotNull(message = "User ID cannot be null")
  private UUID id;

  @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
  @NotNull(message = "First name cannot be null")
  private String firstName;

  @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
  @NotNull(message = "Last name cannot be null")
  private String lastName;

  @NotNull(message = "Role cannot be null")
  @Enumerated(EnumType.STRING)
  private Role role;

  @NotNull(message = "Email cannot be null")
  @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters")
  @Email(message = "Email must be a valid email address")
  private String email;

  @NotNull(message = "Password cannot be null")
  private String password;

  @NotNull(message = "Created at cannot be null")
  private Instant createdAt;
}
