package backend.reports.service;

import model.MedicalReport;
import model.Patient;
import util.MySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReportRepository {

    public Patient getPatient(int patientId) {
        try {
            String query = "SELECT patient_id, name FROM patient WHERE patient_id = ?";
            ResultSet rs = MySQL.executeQuery(query, patientId);
            if (rs.next()) {
                Patient patient = new Patient(rs.getInt("patient_id"), rs.getString("name"));
                rs.close();
                return patient;
            }
            rs.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve patient: " + e.getMessage());
        }
    }

    public void saveReport(MedicalReport report) {
        try {
            System.out.println(report.getPatient().getPatientId());
            String query = "INSERT INTO medical_report (patient_id, content, created_at, type) VALUES (?, ?, NOW(), ?)";
            MySQL.executeUpdate(query, report.getPatient().getPatientId(), report.getContent(), report.getReportTypeId());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save report: " + e.getMessage());
        }
    }

    public List<MedicalReport> getAllReports() {
        try {
            String query = "SELECT mr.medical_report_id, mr.patient_id, mr.content, mr.created_at, mr.type, rt.name AS report_type, p.name AS patient_name "
                    + "FROM medical_report mr JOIN report_type rt ON mr.type = rt.report_type_id "
                    + "JOIN patient p ON mr.patient_id = p.patient_id";
            ResultSet rs = MySQL.executeQuery(query);
            List<MedicalReport> reports = new ArrayList<>();
            while (rs.next()) {
                MedicalReport report = new MedicalReport();
                report.setReportId(rs.getInt("medical_report_id"));
                report.setPatient(new Patient(rs.getInt("patient_id"), rs.getString("patient_name")));
                report.setContent(rs.getString("content"));
                report.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                report.setReportTypeId(rs.getInt("type"));
                report.setReportTypeName(rs.getString("report_type"));
                reports.add(report);
            }
            rs.close();
            return reports;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve reports: " + e.getMessage());
        }
    }

    public String getPatientDetails(int patientId) {
        try {
            String query = "SELECT name, dob, gender, contact_info FROM patient WHERE patient_id = ?";
            ResultSet rs = MySQL.executeQuery(query, patientId);
            if (rs.next()) {
                String details = String.format("Name: %s\nDOB: %s\nGender: %s\nContact: %s",
                        rs.getString("name"),
                        rs.getObject("dob", LocalDate.class),
                        rs.getString("gender"),
                        rs.getString("contact_info"));
                rs.close();
                return details;
            }
            rs.close();
            return "No patient details found";
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve patient details: " + e.getMessage());
        }
    }

    public String getAppointmentHistory(int patientId) {
        try {
            String query = "SELECT date_time, status FROM appointment WHERE patient_id = ?";
            ResultSet rs = MySQL.executeQuery(query, patientId);
            StringBuilder history = new StringBuilder();
            while (rs.next()) {
                history.append(String.format("Date: %s, Status: %s\n",
                        rs.getObject("date_time", LocalDateTime.class),
                        rs.getString("status")));
            }
            rs.close();
            return history.length() > 0 ? history.toString() : "No appointment history";
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve appointment history: " + e.getMessage());
        }
    }

    public String getBillingSummary(int patientId) {
        try {
            String query = "SELECT total, date FROM billing WHERE patient_id = ?";
            ResultSet rs = MySQL.executeQuery(query, patientId);
            StringBuilder summary = new StringBuilder();
            while (rs.next()) {
                summary.append(String.format("Date: %s, Total: %s\n",
                        rs.getObject("date", LocalDate.class),
                        rs.getBigDecimal("total")));
            }
            rs.close();
            return summary.length() > 0 ? summary.toString() : "No billing history";
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve billing summary: " + e.getMessage());
        }
    }

    public String getDiagnosticResults(int patientId) {
        try {
            String query = "SELECT diagnosis_date, result FROM diagnosis WHERE patient_id = ?";
            ResultSet rs = MySQL.executeQuery(query, patientId);
            StringBuilder results = new StringBuilder();
            while (rs.next()) {
                results.append(String.format("Date: %s, Result: %s\n",
                        rs.getObject("diagnosis_date", LocalDate.class),
                        rs.getString("result")));
            }
            rs.close();
            return results.length() > 0 ? results.toString() : "No diagnostic results";
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve diagnostic results: " + e.getMessage());
        }
    }

    public List<String> getReportTypes() {
        try {
            String query = "SELECT name FROM report_type";
            ResultSet rs = MySQL.executeQuery(query);
            List<String> types = new ArrayList<>();
            while (rs.next()) {
                types.add(rs.getString("name"));
            }
            rs.close();
            return types;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve report types: " + e.getMessage());
        }
    }

    public List<Patient> getAllPatients() {
        try {
            String query = "SELECT patient_id, name FROM patient";
            ResultSet rs = MySQL.executeQuery(query);
            List<Patient> patients = new ArrayList<>();
            while (rs.next()) {
                patients.add(new Patient(rs.getInt("patient_id"), rs.getString("name")));
            }
            rs.close();
            return patients;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve patients: " + e.getMessage());
        }
    }
}
