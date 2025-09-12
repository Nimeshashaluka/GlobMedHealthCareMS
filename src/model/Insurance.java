package model;

import java.math.BigDecimal;

public class Insurance {

    private int insuranceId;
    private Patient patient;
    private String providerName;
    private String policyNumber;
    private BigDecimal claimAmount;
    private Status status;
    private String description;

    public enum Status {
        Pending, Approved, Rejected
    }

    // Constructors
    public Insurance() {
    }

    public Insurance(int insuranceId, Patient patient, String providerName, String policyNumber,
            BigDecimal claimAmount, Status status, String description) {
        this.insuranceId = insuranceId;
        this.patient = patient;
        this.providerName = providerName;
        this.policyNumber = policyNumber;
        this.claimAmount = claimAmount;
        this.status = status;
        this.description = description;
    }

    // Getters and Setters
    public int getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(int insuranceId) {
        this.insuranceId = insuranceId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public BigDecimal getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(BigDecimal claimAmount) {
        this.claimAmount = claimAmount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
