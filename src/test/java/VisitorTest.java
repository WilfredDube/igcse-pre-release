import exception.PaymentFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payment.Money;
import payment.PaymentMethod;
import payment.method.PaymentMethodFactory;
import payment.method.PaymentType;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class VisitorTest {
    private Visitor visitor;
    private Booking booking;

    @BeforeEach
    void init(){
        Money price = new Money(Currency.getInstance("USD"), BigDecimal.valueOf(2.50));
        visitor = new Visitor("test name", "testID", "testAddress");
        visitor.setCar(new Car("234-SDFR"));
        booking = new Booking(visitor);
        booking.setParkingSpace(new ParkingSpace(1, price));
    }

    @Test
    @DisplayName("Given a correct payment method, successfully set the visitor's payment method ")
    void chooseCorrectPaymentMethodForVisitor(){
        PaymentMethod paymentMethod = PaymentMethodFactory.getInstance(PaymentType.valueOf("CREDITCARD"));
        visitor.setPaymentMethod(paymentMethod);

        assertEquals("CreditCard{name='creditcard'}", paymentMethod.toString());
    }

    @Test
    @DisplayName("Given an incorrect payment method, throw an IllegalArgumentException exception ")
    void chooseInCorrectPaymentMethodForVisitor(){
        Throwable thrown = assertThrows(IllegalArgumentException.class,
                () -> PaymentMethodFactory.getInstance(PaymentType.valueOf("wrong payment method")));

        assertEquals("No enum constant payment.method.PaymentType.wrong payment method", thrown.getMessage());
    }

    @Test
    @DisplayName("Given that the checkout method executed successfully, pay for a booking successfully")
    void allowSuccessfulCheckoutIfPaymentIsSuccessful() {
        double successRate = 0.9;
        PaymentMethod paymentMethod = new TestPaymentMethod(successRate);
        visitor.setPaymentMethod(paymentMethod);

        assertDoesNotThrow(() -> visitor.checkout(booking));
        assertEquals("Visitor{name='test name', id='testID', address='testAddress', car=Car{licenceNumber='234-SDFR'}, paymentMethod=TestPaymentMethod{name='TestPaymentMethod'}}"
                , visitor.toString());
    }

    @Test
    @DisplayName("Given that the checkout method fails, throw a PaymentFailedException exception")
    void throwsExceptionIfPaymentFails() {
        double successRate = 0.1;
        PaymentMethod paymentMethod = new TestPaymentMethod(successRate);
        visitor.setPaymentMethod(paymentMethod);

        Throwable thrown = assertThrows(PaymentFailedException.class, () -> visitor.checkout(booking));
        assertEquals("Payment process failed. Please try again later.", thrown.getMessage());
    }
}