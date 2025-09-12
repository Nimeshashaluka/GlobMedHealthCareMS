package backend.reports.builder;

import backend.reports.service.ReportService;
import model.MedicalReport;
import java.time.LocalDateTime;
import model.Patient;

public class TreatmentSummaryBuilder implements ReportBuilder {

    private final ReportService reportService;
    private int patientId;
    private StringBuilder content;

    public TreatmentSummaryBuilder(ReportService reportService) {
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
        content.append("Patient Details:\n").append(details).append("\n\n");
        return this;
    }

    @Override
    public ReportBuilder addAppointmentHistory() {
        String history = reportService.getAppointmentHistory(patientId);
        content.append("Appointment History:\n").append(history).append("\n\n");
        return this;
    }

    @Override
    public ReportBuilder addBillingSummary() {
        String summary = reportService.getBillingSummary(patientId);
        content.append("Billing Summary:\n").append(summary).append("\n");
        return this;
    }

    @Override
    public MedicalReport build() {
        Patient patient = reportService.getPatient(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Invalid patient ID: " + patientId);
        }
        return new MedicalReport(0, patient, content.toString(), LocalDateTime.now(), 1); 
    }

    @Override
    public ReportBuilder addDiagnosticResults() {
        return this;
    }
}
