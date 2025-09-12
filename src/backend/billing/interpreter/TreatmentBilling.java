package backend.billing.interpreter;

import java.math.BigDecimal;

public class TreatmentBilling implements BillingExpression {

    private final BigDecimal treatmentCost;

    public TreatmentBilling(BigDecimal treatmentCost) {
        this.treatmentCost = treatmentCost;
    }

    @Override
    public BigDecimal interpret(BillingContext context) {
        context.addBillingItem("Treatment", treatmentCost);
        return treatmentCost;
    }
}
