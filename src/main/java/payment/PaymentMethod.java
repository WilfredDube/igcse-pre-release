package payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentMethod {
    Optional<Payment> mkPayment(UUID bookingID, Money bookingPrice);
}
