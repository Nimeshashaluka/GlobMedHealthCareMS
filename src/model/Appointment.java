package model;

import java.time.LocalDateTime;

public class Appointment {

    private int appointmentId;
    private Patient patient;
    private Staff staff;
    private LocalDateTime dateTime;
    private Status status;

    public enum Status {
        Scheduled, Completed, Cancelled
    }

    // Constructors
    public Appointment() {
    }

    public Appointment(int appointmentId, Patient patient, Staff staff, LocalDateTime dateTime, Status status) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.staff = staff;
        this.dateTime = dateTime;
        this.status = status;
    }

    // Getters and Setters
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
