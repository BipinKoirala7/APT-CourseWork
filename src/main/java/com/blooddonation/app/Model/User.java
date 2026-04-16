package com.blooddonation.app.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
  private UUID id;
  private String firstName;
  private String lastName;
  private Role role;
  private String email;
  private String password;
  private Instant createdAt;
}
