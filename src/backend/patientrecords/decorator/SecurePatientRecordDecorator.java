package backend.patientrecords.decorator;

import backend.security.service.SecurityService;
import model.Patient;

public class SecurePatientRecordDecorator implements PatientRecord {

    private final PatientRecord decoratedRecord;
    private final SecurityService securityService;

    public SecurePatientRecordDecorator(PatientRecord decoratedRecord, SecurityService securityService) {
        this.decoratedRecord = decoratedRecord;
        this.securityService = securityService;
    }

    @Override
    public Patient getPatientData() {
        Patient patient = decoratedRecord.getPatientData();
        if (patient != null) {
            // Decrypt sensitive fields (e.g., name, contact_info)
            patient.setName(decrypt(patient.getName()));
            patient.setContactInfo(decrypt(patient.getContactInfo()));
        }
//        System.out.println(patient);
        return patient;
    }

    @Override
    public void savePatientData(Patient patient) {
        // Encrypt sensitive fields before saving
        Patient encryptedPatient = new Patient(
                patient.getPatientId(),
                encrypt(patient.getName()),
                patient.getDob(),
                patient.getGender(),
                encrypt(patient.getContactInfo()),
                patient.getAddress(),
                patient.getCreatedAt()
        );
        decoratedRecord.savePatientData(encryptedPatient);
    }

    private String encrypt(String data) {
        return securityService.protectData(data);
    }

    private String decrypt(String data) {
        // For simplicity, assume decryption reverses encryption
        // In production, implement proper AES decryption
        return data; // Placeholder
    }
}
