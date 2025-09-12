package backend.billing.interpreter;

import model.Insurance;
import java.math.BigDecimal;

public class InsuranceClaimExpression implements BillingExpression {

    private final Insurance insurance;

    public InsuranceClaimExpression(Insurance insurance) {
        this.insurance = insurance;
    }

    @Override
    public BigDecimal interpret(BillingContext context) {
        BigDecimal claimAmount = insurance.getClaimAmount();
        if (insurance.getStatus() == Insurance.Status.Approved) {
            context.setInsuranceDeduction(claimAmount);
            context.addBillingItem("Insurance Claim", claimAmount.negate()); // Deduct from total
        }
        return claimAmount;
    }
}
