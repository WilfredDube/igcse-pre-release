import exception.PaymentFailedException;
import payment.PaymentMethod;

import java.time.LocalDateTime;

public class Visitor {
    private final String id;
    private final String name;
    private final String address;
    private Car car;
    private PaymentMethod paymentMethod;
    private final LocalDateTime createdOn;

    public Visitor(String name, String id, String address) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.createdOn = LocalDateTime.now();
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void checkout(Booking booking) throws PaymentFailedException {
        var payment = paymentMethod.mkPayment(booking.getBookingID(), booking.getBookingPrice());

        if (payment.isEmpty())
            throw new PaymentFailedException("Payment process failed. Please try again later.");

        booking.setPayment(payment.get());
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", address='" + address  + '\'' +
                ", car=" + car +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
