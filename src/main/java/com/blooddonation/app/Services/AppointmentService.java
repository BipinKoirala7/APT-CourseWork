package com.blooddonation.app.Services;

import com.blooddonation.app.DAO.AppointmentDAO;
import com.blooddonation.app.DTO.AppointmentCreateDTO;
import com.blooddonation.app.Model.Appointment;
import com.blooddonation.app.Model.AppointmentStatus;
import com.blooddonation.app.Security.Validator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class AppointmentService {
  private final AppointmentDAO appointmentDAO;
  private final Validator validator;

  public AppointmentService(AppointmentDAO appointmentDAO) {
    this.appointmentDAO = new AppointmentDAO();
    this.validator = new Validator();
  }

  public void createAppointment(AppointmentCreateDTO appointmentCreateDTO) {
    if (appointmentCreateDTO == null) throw new IllegalArgumentException("AppointmentCreateDTO cannot be null");
    validator.validate(appointmentCreateDTO);
    //  TODO: Get user id from session

    Instant now = Instant.now();

    Appointment appointment = new Appointment(
        UUID.randomUUID(),
        null,
        appointmentCreateDTO.getUserid(),
        appointmentCreateDTO.getType(),
        appointmentCreateDTO.getBloodType(),
        appointmentCreateDTO.getUnits(),
        appointmentCreateDTO.getTime(),
        AppointmentStatus.PENDING,
        now,  // Updated at would be updated once admin approves the appointment
        now
    );
    appointmentDAO.createAppointment(appointment);
  }

  public ArrayList<Appointment> getAppointmentsByUserId(String userId) {
    if (Objects.isNull(userId) || userId.isBlank())
      throw new IllegalArgumentException("User ID cannot be null or blank");

    return appointmentDAO.getAllAppointmentsByUserId(userId);
  }

  public void approveAppointment(String id, String adminId) {
    if (Objects.isNull(id) || id.isBlank())
      throw new IllegalArgumentException("Appointment ID cannot be null or blank");
    if (Objects.isNull(adminId) || adminId.isBlank())
      throw new IllegalArgumentException("Admin ID cannot be null or blank");

    appointmentDAO.updateAppointmentStatus(id, adminId, AppointmentStatus.APPROVED);
  }

  public void completeAppointment(String appointmentId) {
    if (Objects.isNull(appointmentId) || appointmentId.isBlank())
      throw new IllegalArgumentException("Appointment ID cannot be null or blank");

    appointmentDAO.updateAppointmentStatus(appointmentId, AppointmentStatus.COMPLETED);
  }

  public void deleteAppointment(String id) {
    if (Objects.isNull(id) || id.isBlank())
      throw new IllegalArgumentException("Appointment ID cannot be null or blank");

    appointmentDAO.deleteAppointment(id);
  }
}
