package com.blooddonation.app.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class DonorAppointment extends Appointment {
  private UUID donorId;
  private BloodType bloodType;
}
