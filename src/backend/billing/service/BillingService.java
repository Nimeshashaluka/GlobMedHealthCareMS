package backend.billing.service;

import backend.billing.interpreter.BillingContext;
import backend.billing.interpreter.BillingExpression;
import backend.billing.interpreter.ConsultationBilling;
import backend.billing.interpreter.TreatmentBilling;
import backend.billing.interpreter.InsuranceClaimExpression;
import backend.security.service.SecurityService;
import backend.staffmanagement.composite.StaffComponent;
import model.Billing;
import model.Insurance;
import model.Patient;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BillingService {

    private final BillingRepository repository;
    private final SecurityService securityService;

    public BillingService(SecurityService securityService) {
        this.repository = new BillingRepository();
        this.securityService = securityService;
    }

    public Billing processBilling(int patientId, StaffComponent staff, BigDecimal consultationFee, BigDecimal treatmentCost, Insurance insurance) {
        if (!staff.hasPermission("process_billing")) {
            securityService.logAccess(String.valueOf(staff.getId()), "UNAUTHORIZED_BILLING", "billing", "192.168.1.1");
            throw new SecurityException("Unauthorized to process billing");
        }

        BillingContext context = new BillingContext(patientId);
        // Build expression tree
        BillingExpression consultation = new ConsultationBilling(consultationFee);
        BillingExpression treatment = new TreatmentBilling(treatmentCost);
        BillingExpression insuranceClaim = insurance != null ? new InsuranceClaimExpression(insurance) : null;

        // Interpret expressions
        consultation.interpret(context);
        treatment.interpret(context);
        if (insuranceClaim != null) {
            insuranceClaim.interpret(context);
        }

        // Create billing record
        Patient patient = new Patient();
        patient.setPatientId(patientId);
        Billing billing = new Billing(0, patient, context.getTotal(), LocalDate.now());
        repository.saveBilling(billing, context.getBillingItems());

        securityService.logAccess(String.valueOf(staff.getId()), "PROCESS_BILLING", "billing", "192.168.1.1");
        return billing;
    }

    public Insurance processInsuranceClaim(int patientId, String providerName, String policyNumber, BigDecimal claimAmount, String description, StaffComponent staff) {
        if (!staff.hasPermission("process_billing")) {
            securityService.logAccess(String.valueOf(staff.getId()), "UNAUTHORIZED_CLAIM", "insurance", "192.168.1.1");
            throw new SecurityException("Unauthorized to process insurance claim");
        }

        Patient patient = new Patient();
        patient.setPatientId(patientId);
        Insurance insurance = new Insurance(0, patient, providerName, policyNumber, claimAmount, Insurance.Status.Pending, description);
        repository.saveInsurance(insurance);
        securityService.logAccess(String.valueOf(staff.getId()), "PROCESS_INSURANCE_CLAIM", "insurance", "192.168.1.1");
        return insurance;
    }
}