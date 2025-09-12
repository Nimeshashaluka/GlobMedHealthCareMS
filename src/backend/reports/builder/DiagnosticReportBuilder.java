package backend.reports.builder;

import backend.reports.service.ReportService;
import model.MedicalReport;
import model.Patient;
import java.time.LocalDateTime;

public class DiagnosticReportBuilder implements ReportBuilder {

    private final ReportService reportService;
    private int patientId;
    private StringBuilder content;

    public DiagnosticReportBuilder(ReportService reportService) {
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
        content.append("Patient Diagnostic Report:\n").append(details).append("\n\n");
        return this;
    }

    @Override
    public ReportBuilder addAppointmentHistory() {
        // Skip for diagnostic report
        return this;
    }

    @Override
    public ReportBuilder addBillingSummary() {
        // Skip for diagnostic report
        return this;
    }

    public ReportBuilder addDiagnosticResults() {
        String results = reportService.getDiagnosticResults(patientId);
        content.append("Diagnostic Results:\n").append(results).append("\n");
        return this;
    }

    @Override
    public MedicalReport build() {
        Patient patient = reportService.getPatient(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Invalid patient ID: " + patientId);
        }
        return new MedicalReport(0, patient, content.toString(), LocalDateTime.now(), 3); 
    }

}
