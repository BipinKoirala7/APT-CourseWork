package com.blooddonation.app.Model;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class User {
  private UUID id;
  private String first_name;
  private String last_name;
  private String email;
  private String password;
  private Instant created_at;
}
