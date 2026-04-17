package com.blooddonation.app.DTO;

import lombok.Getter;

@Getter
public class UserUpdateDTO extends UserCreateDTO {
  private String id;

  public UserUpdateDTO(String id, String first_name, String last_name, String email, String password) {
    this.id = id;
    super(first_name, last_name, email, password);
  }
}
