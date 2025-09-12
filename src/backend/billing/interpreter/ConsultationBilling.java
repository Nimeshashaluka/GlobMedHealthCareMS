package backend.billing.interpreter;

import java.math.BigDecimal;

public class ConsultationBilling implements BillingExpression {

    private final BigDecimal consultationFee;

    public ConsultationBilling(BigDecimal consultationFee) {
        this.consultationFee = consultationFee;
    }

    @Override
    public BigDecimal interpret(BillingContext context) {
        context.addBillingItem("Consultation", consultationFee);
        return consultationFee;
    }
}
