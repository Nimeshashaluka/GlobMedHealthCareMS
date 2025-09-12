package backend.patientrecords.decorator;

import backend.patientrecords.service.PatientRecordService;
import model.Patient;

public class BasicPatientRecord implements PatientRecord {
    private final PatientRecordService service;

    public BasicPatientRecord(PatientRecordService service) {
        this.service = service;
    }

    @Override
    public Patient getPatientData() {
        return service.getPatient();
    }

    @Override
    public void savePatientData(Patient patient) {
        service.savePatient(patient);
    }
}