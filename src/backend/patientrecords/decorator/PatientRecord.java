package backend.patientrecords.decorator;

import model.Patient;

public interface PatientRecord {

    Patient getPatientData();

    void savePatientData(Patient patient);
}
