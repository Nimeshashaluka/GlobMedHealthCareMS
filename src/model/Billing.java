package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Billing {

    private int billingId;
    private Patient patient;
    private BigDecimal total;
    private LocalDate date;

    // Constructors
    public Billing() {
    }

    public Billing(int billingId, Patient patient, BigDecimal total, LocalDate date) {
        this.billingId = billingId;
        this.patient = patient;
        this.total = total;
        this.date = date;
    }

    // Getters and Setters
    public int getBillingId() {
        return billingId;
    }

    public void setBillingId(int billingId) {
        this.billingId = billingId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
