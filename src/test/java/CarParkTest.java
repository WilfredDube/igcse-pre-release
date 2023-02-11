import exception.NoParkingSpaceException;
import exception.PaymentFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class CarParkTest {
    private Visitor newVisitor;
    private CarPark carPark;

    @BeforeEach
    void init() {
        double probabilityOfSuccess = 0.9;
        carPark = new CarPark("Test parking lot");
        Car car = new Car("234-X3");
        newVisitor = new Visitor("name", "id", "address");
        newVisitor.setCar(car);
        newVisitor.setPaymentMethod(new TestPaymentMethod(probabilityOfSuccess));
    }

    @Test
    @DisplayName("Given that there are parking spaces available on the selected date add first booking")
    void addBooking() {
        Booking booking = new Booking(newVisitor);
        assertDoesNotThrow(() -> carPark.addBooking(booking, LocalDateTime.now()));
        assertEquals(booking.getBookingDate(), LocalDate.now());
    }

    @Test
    @DisplayName("Given that all parking spaces are already booked for the entire booking period throws a NoParkingSpaceException exception.")
    void throwsExceptionWhenAllParkingSpacesHaveBeenBooked() {
        var bookedDate = LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.now());
        LocalDate upComingDate = LocalDate.now().plusDays(15);

        fillAllAvailableBookings();
        Booking booking = new Booking(newVisitor);

        RuntimeException thrown = assertThrows(NoParkingSpaceException.class, () -> carPark.addBooking(booking, bookedDate));
        assertEquals("No free parking space for the next 14 days. Should we schedule the booking on: " +
                upComingDate + "?", thrown.getMessage());
    }

    // Fills up all the parking spaces on each day of the entire booking period.
    private void fillAllAvailableBookings() {
        for (int i = 0; i < CarPark.BOOKING_PERIOD; i++) {
            var bookedDate = LocalDateTime.of(LocalDate.now().plusDays(i), LocalTime.now());
            for (int j = 0; j < CarPark.PARKING_SPACES; j++) {
                Booking booking = new Booking(newVisitor);
                carPark.addBooking(booking, bookedDate);
            }
        }
    }

    @Test
    @DisplayName("Given that there are no parking spaces on the selected date throw a NoParkingSpaceException exception.")
    void throwsExceptionGivenThatNoParkingSpacesAreAvailableOnASelectedDate() {
        var bookedDate = LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.now());

        for (int i = 0; i < CarPark.PARKING_SPACES; i++) {
            Booking booking = new Booking(newVisitor);
            carPark.addBooking(booking, bookedDate);
        }

        Booking booking = new Booking(newVisitor);
        NoParkingSpaceException thrown = assertThrows(NoParkingSpaceException.class, () -> carPark.addBooking(booking, bookedDate));

        assertEquals("No free parking space on this particular day: " + bookedDate, thrown.getMessage());
    }

    @Test
    @DisplayName("Given that there are parking spaces on the selected date but payment fails throw a PaymentFailedException exception.")
    void hasParkingSpacesOnASelectedDateButPaymentFails() {
        var bookedDate = LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.now());

        newVisitor.setPaymentMethod(new TestPaymentMethod(0.1));
        Booking booking = new Booking(newVisitor);
        PaymentFailedException thrown = assertThrows(PaymentFailedException.class, () -> carPark.addBooking(booking, bookedDate));

        assertEquals("Payment process failed. Please try again later.", thrown.getMessage());
    }
}
