package backend.patientrecords.service;

import model.Patient;
import util.MySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PatientRecordRepository {

    private int patientId;

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Patient getPatient() {
        try {
            String query = "SELECT * FROM patient WHERE patient_id = ?";
            ResultSet rs = MySQL.executeQuery(query, patientId);
            if (rs.next()) {
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));
                patient.setName(rs.getString("name"));
                patient.setDob(rs.getObject("dob", LocalDate.class));
                patient.setGender(Patient.Gender.valueOf(rs.getString("gender")));
                patient.setContactInfo(rs.getString("contact_info"));
                patient.setAddress(rs.getString("address"));
                patient.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                rs.close();
                return patient;
            }
            rs.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve patient: " + e.getMessage());
        }
    }

    public List<Patient> getAllPatients() {
        try {
            String query = "SELECT * FROM patient";
            ResultSet rs = MySQL.executeQuery(query);
            List<Patient> patients = new ArrayList<>();
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));
                patient.setName(rs.getString("name"));
                patient.setDob(rs.getObject("dob", LocalDate.class));
                patient.setGender(Patient.Gender.valueOf(rs.getString("gender")));
                patient.setContactInfo(rs.getString("contact_info"));
                patient.setAddress(rs.getString("address"));
                patient.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                patients.add(patient);
//                System.out.println(patient);
            }
            rs.close();
            return patients;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all patients: " + e.getMessage());
        }
    }

    public List<Patient> searchPatients(String searchQuery) {
        try {
            String query = "SELECT * FROM patient WHERE name LIKE ? OR patient_id = ?";
            ResultSet rs = MySQL.executeQuery(query, "%" + searchQuery + "%", searchQuery);
            List<Patient> patients = new ArrayList<>();
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));
                patient.setName(rs.getString("name"));
                patient.setDob(rs.getObject("dob", LocalDate.class));
                patient.setGender(Patient.Gender.valueOf(rs.getString("gender")));
                patient.setContactInfo(rs.getString("contact_info"));
                patient.setAddress(rs.getString("address"));
                patient.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                patients.add(patient);
            }
            rs.close();
            return patients;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to search patients: " + e.getMessage());
        }
    }

    public void savePatient(Patient patient) {
        try {
            if (patient.getPatientId() == 0) {
                String query = "INSERT INTO patient (name, dob, gender, contact_info, address, created_at) VALUES (?, ?, ?, ?, ?, NOW())";
                MySQL.executeUpdate(query, patient.getName(), patient.getDob(), patient.getGender().name(), patient.getContactInfo(), patient.getAddress());
            } else {
                String query = "UPDATE patient SET name = ?, dob = ?, gender = ?, contact_info = ?, address = ? WHERE patient_id = ?";
                MySQL.executeUpdate(query, patient.getName(), patient.getDob(), patient.getGender().name(), patient.getContactInfo(), patient.getAddress(), patient.getPatientId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save patient: " + e.getMessage());
        }
    }
}
