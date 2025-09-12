package backend.reports.builder;

import model.MedicalReport;

public interface ReportBuilder {

    ReportBuilder setPatientId(int patientId);

    ReportBuilder addPatientDetails();

    ReportBuilder addAppointmentHistory();

    ReportBuilder addBillingSummary();

    ReportBuilder addDiagnosticResults();

    MedicalReport build();
}
