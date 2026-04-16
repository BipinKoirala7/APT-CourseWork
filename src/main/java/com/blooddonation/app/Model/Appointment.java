package com.blooddonation.app.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Appointment {
  private UUID id;
  private UUID adminId;
  private UUID userid;
  private AppointmentType type;
  private BloodType bloodType;
  private Integer units;
  private Instant time;
  private AppointmentStatus status;
  private Instant createdAt;
  private Instant updatedAt;
}
