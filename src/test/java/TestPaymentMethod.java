import payment.Money;
import payment.Payment;
import payment.PaymentMethod;

import java.util.Optional;
import java.util.UUID;

public class TestPaymentMethod implements PaymentMethod {
    private final double successRate;
    private final String name;

    private TestPaymentMethod(double successRate, String name) {
        this.successRate = successRate;
        this.name = name;
    }

    TestPaymentMethod(double successRate){
        this(successRate, "TestPaymentMethod");
    }

    @Override
    public Optional<Payment> mkPayment(UUID bookingID, Money bookingPrice) {
        return (successRate > 0.3) ?
                Optional.of(new Payment(bookingID, bookingPrice)) :
                Optional.empty();
    }

    @Override
    public String toString() {
        return "TestPaymentMethod{" +
                "name='" + name + '\'' +
                '}';
    }
}