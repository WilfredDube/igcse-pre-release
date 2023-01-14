import exception.PaymentFailedException;
import payment.Money;
import payment.Payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Booking {
    private final UUID bookingID;
    private final Visitor visitor;
    private ParkingSpace parkingSpace;
    private Payment payment;
    private final LocalDateTime createdOn;
    private LocalDateTime bookingDate;

    public Booking(Visitor visitor) {
        this.visitor = visitor;
        this.bookingID = UUID.randomUUID();
        this.createdOn = LocalDateTime.now();
    }

    public void setBookingDate(LocalDateTime date) {
        this.bookingDate = date;
    }

    public void pay() throws PaymentFailedException {
        this.visitor.checkout(this);
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public LocalDate getBookingDate() {
        return bookingDate.toLocalDate();
    }

    public Money getBookingPrice() {
        return parkingSpace.getPrice();
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public UUID getBookingID() {
        return bookingID;
    }

    @Override
    public String toString() {
        return "\n\t\tBooking{" +
                "\n\t\t\tbookingID=" + bookingID +
                ", \n\t\t\tvisitor=" + visitor +
                ", \n\t\t\tparkingSpace=" + parkingSpace +
                ", \n\t\t\tpayment=" + payment +
                ", \n\t\t\tcreatedOn=" + createdOn +
                ", \n\t\t\tbookingForDate=" + bookingDate +
                "\n\t\t}";
    }
}
