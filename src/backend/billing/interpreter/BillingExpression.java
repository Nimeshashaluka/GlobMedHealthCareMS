package backend.billing.interpreter;

import java.math.BigDecimal;

public interface BillingExpression {

    BigDecimal interpret(BillingContext context);
}
