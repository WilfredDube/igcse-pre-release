package payment;

import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
    private final UUID id;
    private final UUID bookingID;
    private final Money cost;
    private final LocalDateTime createdOn;

    public Payment(UUID bookingID, Money cost) {
        this.id = UUID.randomUUID();
        this.bookingID = bookingID;
        this.cost = cost;
        this.createdOn = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "payment.Payment{" +
                "id=" + id +
                ", bookingID=" + bookingID +
                ", cost=" + cost +
                ", createdOn=" + createdOn +
                '}';
    }
}
