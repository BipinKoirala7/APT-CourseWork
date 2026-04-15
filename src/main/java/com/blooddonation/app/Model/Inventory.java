package com.blooddonation.app.Model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Inventory {
  private UUID id;
  private UUID donorId;
  private Integer units;
  private BloodType bloodType;
  private LocalDateTime expiryDate;
  private LocalDateTime createdAt;
}
