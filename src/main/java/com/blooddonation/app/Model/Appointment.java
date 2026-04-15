package com.blooddonation.app.Model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
abstract class Appointment {
  protected UUID id;
  protected UUID adminId;
  protected LocalDateTime time;
  protected AppointmentStatus status;
  protected LocalDateTime createdAt;
  protected LocalDateTime updatedAt;
}
