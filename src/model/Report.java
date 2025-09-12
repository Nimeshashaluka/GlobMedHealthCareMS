package model;

import java.time.LocalDateTime;

public class Report {

    private int reportId;
    private Patient patient;
    private ReportType reportType;
    private LocalDateTime createdAt;

    // Constructors
    public Report() {
    }

    public Report(int reportId, Patient patient, ReportType reportType, LocalDateTime createdAt) {
        this.reportId = reportId;
        this.patient = patient;
        this.reportType = reportType;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
