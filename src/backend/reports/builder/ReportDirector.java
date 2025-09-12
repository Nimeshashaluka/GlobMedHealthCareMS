package backend.reports.builder;

import model.MedicalReport;

public class ReportDirector {

    private ReportBuilder builder;

    public ReportDirector(ReportBuilder builder) {
        this.builder = builder;
    }

    public MedicalReport constructFullReport(int patientId) {
        System.out.println(patientId);
        return builder.setPatientId(patientId)
                .addPatientDetails()
                .addAppointmentHistory()
                .addBillingSummary()
                .build();
    }

    public MedicalReport constructFinancialReport(int patientId) {
        return builder.setPatientId(patientId)
                .addPatientDetails()
                .addBillingSummary()
                .build();
    }

    public MedicalReport constructDiagnosticReport(int patientId) {
        return builder.setPatientId(patientId)
                .addPatientDetails()
                .addDiagnosticResults()
                .build();
    }
}
