package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Patient {

    private int patientId;
    private String name;
    private LocalDate dob;
    private Gender gender;
    private String contactInfo;
    private String address;
    private LocalDateTime createdAt;

    public enum Gender {
        M, F, Other
    }

    // Constructors
    public Patient() {
    }

    public Patient(int patientId, String name) {
        this.patientId = patientId;
        this.name = name;
        
    }
    
    public Patient(int patientId, String name, LocalDate dob, Gender gender, String contactInfo, String address, LocalDateTime createdAt) {
        this.patientId = patientId;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.address = address;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
