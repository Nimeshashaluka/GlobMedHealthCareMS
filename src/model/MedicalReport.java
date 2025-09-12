package model;

import java.time.LocalDateTime;

public class MedicalReport {

    private int reportId;
    private Patient patient;
    private String content;
    private LocalDateTime createdAt;
    private int reportTypeId;
    private String reportTypeName;

    public MedicalReport() {
    }

    public MedicalReport(int reportId, Patient patient, String content, LocalDateTime createdAt, int reportTypeId) {
        this.reportId = reportId;
        this.patient = patient;
        this.content = content;
        this.createdAt = createdAt;
        this.reportTypeId = reportTypeId;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(int reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    public String getReportTypeName() {
        return reportTypeName;
    }

    public void setReportTypeName(String reportTypeName) {
        this.reportTypeName = reportTypeName;
    }
}
