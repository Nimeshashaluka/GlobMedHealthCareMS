package backend.billing.interpreter;

import model.BillingItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BillingContext {

    private final int patientId;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal insuranceDeduction = BigDecimal.ZERO;
    private final List<BillingItem> billingItems = new ArrayList<>();

    public BillingContext(int patientId) {
        this.patientId = patientId;
    }

    public void addBillingItem(String description, BigDecimal amount) {
        billingItems.add(new BillingItem(0, null, description, amount));
        total = total.add(amount);
    }

    public void setInsuranceDeduction(BigDecimal amount) {
        this.insuranceDeduction = amount;
    }

    public int getPatientId() {
        return patientId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<BillingItem> getBillingItems() {
        return billingItems;
    }

    public BigDecimal getInsuranceDeduction() {
        return insuranceDeduction;
    }
}
