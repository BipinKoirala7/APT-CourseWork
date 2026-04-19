package com.blooddonation.app.DTO;

import com.blooddonation.app.Model.AppointmentType;
import com.blooddonation.app.Model.BloodType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class AppointmentCreateDTO {

  @NotNull(message = "User ID cannot be null")
  private UUID userid;

  @NotNull(message = "Appointment type cannot be null")
  @Enumerated(value = EnumType.STRING)
  private AppointmentType type;

  @NotNull(message = "Blood type cannot be null")
  @Enumerated(value = EnumType.STRING)
  private BloodType bloodType;

  @NotNull(message = "Units cannot be null")
  private Integer units;

  @NotNull(message = "Time cannot be null")
  private Instant time;
}
