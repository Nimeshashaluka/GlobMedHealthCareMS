package backend.patientrecords.decorator;

import backend.security.service.SecurityService;
import backend.staffmanagement.composite.StaffComponent;
import model.Patient;

public class AccessControlDecorator implements PatientRecord {

    private final PatientRecord decoratedRecord;
    private final StaffComponent staff;
    private final SecurityService securityService;

    public AccessControlDecorator(PatientRecord decoratedRecord, StaffComponent staff, SecurityService securityService) {
        this.decoratedRecord = decoratedRecord;
        this.staff = staff;
        this.securityService = securityService;
    }

    @Override
    public Patient getPatientData() {
        if (!staff.hasPermission("view_records")) {
            securityService.logAccess(String.valueOf(staff.getId()), "UNAUTHORIZED_ACCESS", "patient", "192.168.1.1");
            throw new SecurityException("Unauthorized access to patient records");
        }
        securityService.logAccess(String.valueOf(staff.getId()), "VIEW_PATIENT", "patient", "192.168.1.1");
        return decoratedRecord.getPatientData();
    }

    @Override
    public void savePatientData(Patient patient) {
        if (!staff.hasPermission("update_records")) {
            securityService.logAccess("unknown", "UNAUTHORIZED_SAVE", "patient", "192.168.1.1");
            throw new SecurityException("Unauthorized save of patient records");
        }
        securityService.logAccess("unknown", "SAVE_PATIENT", "patient", "192.168.1.1");
        decoratedRecord.savePatientData(patient);
    }
}
