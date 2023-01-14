package payment;

import java.math.BigDecimal;
import java.util.Currency;

public class Money {
    private final Currency currency;
    private final BigDecimal amount;

    public Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return currency.getSymbol() +
                amount;
    }
}
