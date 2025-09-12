package model;

import java.math.BigDecimal;

public class BillingItem {

    private int billingItemId;
    private Billing billing;
    private String description;
    private BigDecimal amount;

    // Constructors
    public BillingItem() {
    }

    public BillingItem(int billingItemId, Billing billing, String description, BigDecimal amount) {
        this.billingItemId = billingItemId;
        this.billing = billing;
        this.description = description;
        this.amount = amount;
    }

    // Getters and Setters
    public int getBillingItemId() {
        return billingItemId;
    }

    public void setBillingItemId(int billingItemId) {
        this.billingItemId = billingItemId;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
