package backend.patientrecords.service;

import backend.patientrecords.decorator.AccessControlDecorator;
import backend.patientrecords.decorator.BasicPatientRecord;
import backend.patientrecords.decorator.PatientRecord;
import backend.patientrecords.decorator.SecurePatientRecordDecorator;
import backend.security.service.SecurityService;
import backend.staffmanagement.composite.StaffComponent;
import model.Patient;
import java.util.List;
import java.util.stream.Collectors;

public class PatientRecordService {

    private final PatientRecordRepository repository;
    private final SecurityService securityService;

    public PatientRecordService(SecurityService securityService) {
        this.repository = new PatientRecordRepository();
        this.securityService = securityService;
    }

    public PatientRecord getPatientRecord(int patientId, StaffComponent staff) {
        repository.setPatientId(patientId);
        PatientRecord record = new BasicPatientRecord(this);
        record = new SecurePatientRecordDecorator(record, securityService);
        record = new AccessControlDecorator(record, staff, securityService);
        return record;
    }

    public List<Patient> getAllPatients(StaffComponent staff, String ipAddress) {
        if (!staff.hasPermission("view_records")) {
            securityService.logAccess(String.valueOf(staff.getId()), "UNAUTHORIZED_ACCESS", "patient", ipAddress);
            throw new SecurityException("Unauthorized access to patient records");
        }
        List<Patient> patients = repository.getAllPatients();
//        System.out.println(patients);
        securityService.logAccess(String.valueOf(staff.getId()), "VIEW_ALL_PATIENTS", "patient", ipAddress);
        return patients.stream()
                .map(patient -> {
                    PatientRecord record = new PatientRecord() {
                        @Override
                        public Patient getPatientData() {
                            return patient;
                        }

                        @Override
                        public void savePatientData(Patient p) {
                            repository.savePatient(p);
                        }
                    };
                    return new SecurePatientRecordDecorator(record, securityService).getPatientData();
                })
                .collect(Collectors.toList());
    }

    public List<Patient> searchPatients(String searchQuery, StaffComponent staff, String ipAddress) {
        if (!staff.hasPermission("view_records")) {
            securityService.logAccess(String.valueOf(staff.getId()), "UNAUTHORIZED_ACCESS", "patient", ipAddress);
            throw new SecurityException("Unauthorized access to patient records");
        }
        List<Patient> patients = repository.searchPatients(searchQuery);
        securityService.logAccess(String.valueOf(staff.getId()), "SEARCH_PATIENT", "patient", ipAddress);

        return patients.stream()
                .map(patient -> {
                    PatientRecord record = new PatientRecord() {
                        @Override
                        public Patient getPatientData() {
                            return patient;
                        }

                        @Override
                        public void savePatientData(Patient p) {
                            repository.savePatient(p);
                        }
                    };
                    return new SecurePatientRecordDecorator(record, securityService).getPatientData();
                })
                .collect(Collectors.toList());
    }

    public void savePatient(Patient patient, StaffComponent staff, String ipAddress) {
        PatientRecord record = new BasicPatientRecord(this);
        record = new SecurePatientRecordDecorator(record, securityService);
        record = new AccessControlDecorator(record, staff, securityService);
        record.savePatientData(patient);
    }

    // Called by BasicPatientRecord
    public Patient getPatient() {
        return repository.getPatient();
    }

    public void setPatientId(int id) {
        repository.setPatientId(id);
    }

    public void savePatient(Patient patient) {
        repository.savePatient(patient);
    }
}
