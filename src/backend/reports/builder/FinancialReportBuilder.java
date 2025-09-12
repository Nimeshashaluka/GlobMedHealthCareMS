package backend.reports.builder;

import backend.reports.service.ReportService;
import model.MedicalReport;
import java.time.LocalDateTime;
import model.Patient;

public class FinancialReportBuilder implements ReportBuilder {

    private final ReportService reportService;
    private int patientId;
    private StringBuilder content;

    public FinancialReportBuilder(ReportService reportService) {
        this.reportService = reportService;
        this.content = new StringBuilder();
    }

    @Override
    public ReportBuilder setPatientId(int patientId) {
        this.patientId = patientId;
        return this;
    }

    @Override
    public ReportBuilder addPatientDetails() {
        String details = reportService.getPatientDetails(patientId);
        content.append("Patient Financial Report:\n").append(details).append("\n\n");
        return this;
    }

    @Override
    public ReportBuilder addAppointmentHistory() {
        // Skip for financial report
        return this;
    }

    @Override
    public ReportBuilder addBillingSummary() {
        String summary = reportService.getBillingSummary(patientId);
        content.append("Financial Details:\n").append(summary).append("\n");
        return this;
    }

    @Override
    public MedicalReport build() {
        Patient patient = reportService.getPatient(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Invalid patient ID: " + patientId);
        }
        return new MedicalReport(0, patient, content.toString(), LocalDateTime.now(), 2);
    }

    @Override
    public ReportBuilder addDiagnosticResults() {
        return this;
    }
}
