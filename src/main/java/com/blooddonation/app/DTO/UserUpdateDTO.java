package com.blooddonation.app.DTO;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserUpdateDTO extends UserCreateDTO {
  private UUID id;

  public UserUpdateDTO(String first_name, String last_name, String email, String password) {
    super(first_name, last_name, email, password);
  }
}
