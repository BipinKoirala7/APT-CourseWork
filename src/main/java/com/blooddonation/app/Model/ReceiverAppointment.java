package com.blooddonation.app.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReceiverAppointment extends Appointment {
  private UUID receiverId;
  private BloodType bloodType;
  private Integer unitsNeeded;
}
