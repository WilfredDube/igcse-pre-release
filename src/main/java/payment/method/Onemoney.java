package payment.method;

import payment.Money;
import payment.Payment;
import payment.PaymentMethod;

import java.util.Optional;
import java.util.UUID;

public class Onemoney implements PaymentMethod {
    private final String name;

    public Onemoney(String name) {
        this.name = name;
    }

    @Override
    public Optional<Payment> mkPayment(UUID bookingID, Money bookingPrice) {
        return (Math.random() > 0.3) ?
                Optional.of(new Payment(bookingID, bookingPrice)) :
                Optional.empty();
    }

    @Override
    public String toString() {
        return "Onemoney{" +
                "name='" + name + '\'' +
                '}';
    }
}
