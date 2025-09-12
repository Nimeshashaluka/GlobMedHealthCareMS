package backend.reports.service;

import backend.reports.builder.FinancialReportBuilder;
import backend.reports.builder.ReportBuilder;
import backend.reports.builder.TreatmentSummaryBuilder;
import backend.reports.builder.DiagnosticReportBuilder;
import backend.reports.builder.ReportDirector;
import backend.security.service.SecurityService;
import backend.staffmanagement.composite.StaffComponent;
import java.util.List;
import model.MedicalReport;
import model.Patient;

public class ReportService {

    private final ReportRepository repository;
    private final SecurityService securityService;

    public ReportService(SecurityService securityService) {
        this.repository = new ReportRepository();
        this.securityService = securityService;
    }

    public MedicalReport generateTreatmentSummary(int patientId, StaffComponent staff) {
        if (!staff.hasPermission("create_reports")) {
            securityService.logAccess(String.valueOf(staff.getId()), "UNAUTHORIZED_REPORT", "medical_report", "192.168.1.1");
            throw new SecurityException("Unauthorized to generate report");
        }

        ReportBuilder builder = new TreatmentSummaryBuilder(this);
        ReportDirector director = new ReportDirector(builder);
        MedicalReport report = director.constructFullReport(patientId);
        repository.saveReport(report);
        securityService.logAccess(String.valueOf(staff.getId()), "GENERATE_TREATMENT_SUMMARY", "medical_report", "192.168.1.1");
        return report;
    }

    public MedicalReport generateFinancialReport(int patientId, StaffComponent staff) {
        if (!staff.hasPermission("create_reports")) {
            securityService.logAccess(String.valueOf(staff.getId()), "UNAUTHORIZED_REPORT", "medical_report", "192.168.1.1");
            throw new SecurityException("Unauthorized to generate report");
        }

        ReportBuilder builder = new FinancialReportBuilder(this);
        ReportDirector director = new ReportDirector(builder);
        MedicalReport report = director.constructFinancialReport(patientId);
        repository.saveReport(report);
        securityService.logAccess(String.valueOf(staff.getId()), "GENERATE_FINANCIAL_REPORT", "medical_report", "192.168.1.1");
        return report;
    }

    public MedicalReport generateDiagnosticReport(int patientId, StaffComponent staff) {
        if (!staff.hasPermission("create_reports")) {
            securityService.logAccess(String.valueOf(staff.getId()), "UNAUTHORIZED_REPORT", "medical_report", "192.168.1.1");
            throw new SecurityException("Unauthorized to generate report");
        }

        ReportBuilder builder = new DiagnosticReportBuilder(this);
        ReportDirector director = new ReportDirector(builder);
        MedicalReport report = director.constructDiagnosticReport(patientId);
        repository.saveReport(report);
        securityService.logAccess(String.valueOf(staff.getId()), "GENERATE_DIAGNOSTIC_REPORT", "medical_report", "192.168.1.1");
        return report;
    }

    public String getPatientDetails(int patientId) {
        return repository.getPatientDetails(patientId);
    }

    public String getAppointmentHistory(int patientId) {
        return repository.getAppointmentHistory(patientId);
    }

    public String getBillingSummary(int patientId) {
        return repository.getBillingSummary(patientId);
    }

    public String getDiagnosticResults(int patientId) {
        return repository.getDiagnosticResults(patientId);
    }

    public List<MedicalReport> getAllReports() {
        return repository.getAllReports();
    }

    public List<String> getReportTypes() {
        return repository.getReportTypes();
    }

    public List<Patient> getAllPatients() {
        return repository.getAllPatients();
    }

    public Patient getPatient(int patientId) {
        return repository.getPatient(patientId);
    }

}
