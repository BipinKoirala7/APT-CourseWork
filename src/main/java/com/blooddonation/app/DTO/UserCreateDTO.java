package com.blooddonation.app.DTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserCreateDTO {
  private String first_name;
  private String last_name;
  private String email;
  private String password;
}
