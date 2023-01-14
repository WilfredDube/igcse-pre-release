import exception.InvalidDateException;
import exception.NoParkingSpaceException;
import exception.PaymentFailedException;
import payment.Money;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class CarPark {
    public static final int PARKING_SPACES = 2;
    public static final float PRICE_PER_PARKING_SPACE = 3.0f;
    public static final String CURRENCY_CODE = "USD";
    public static final int BOOKING_PERIOD = 14;
    public static final int TOTAL_BOOKINGS = BOOKING_PERIOD * PARKING_SPACES;

    private final List<Booking> bookings = new ArrayList<>();
    private final String organization;

    public CarPark(String organization) {
        this.organization = organization;
    }

    public void addBooking(Booking booking, LocalDateTime bookingDate) throws NoParkingSpaceException, PaymentFailedException {
        long count = checkAvailableParkingSpace(bookingDate);

        booking.setBookingDate(bookingDate);

        Money parkingCost = new Money(Currency.getInstance(CURRENCY_CODE), BigDecimal.valueOf(PRICE_PER_PARKING_SPACE));
        booking.setParkingSpace(new ParkingSpace((int) count + 1, parkingCost));

        booking.pay();

        this.bookings.add(booking);
    }

    private long checkAvailableParkingSpace(LocalDateTime bookingDate) {
        if (this.bookings.size() == TOTAL_BOOKINGS) {
            throw new NoParkingSpaceException("No free parking space for the next " + BOOKING_PERIOD + " days." +
                    "Should we schedule the booking on: " + LocalDate.now().plusDays(BOOKING_PERIOD + 1) +
                    "?");
        }

        long count = countBookingsOn(bookingDate);
        if (count == PARKING_SPACES) {
            throw new NoParkingSpaceException("No free parking space on this particular day: " + bookingDate);
        }

        return count;
    }

    public void validDateFormat(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setLenient(false);
        format.parse(date);
    }

    public void validateBookingDate(LocalDateTime bookingDate) throws InvalidDateException {
        if (bookingDate.toLocalDate().isBefore(LocalDate.now()))
            throw new InvalidDateException("Booking date: " + bookingDate
                    + " can not be before the current date: "
                    + LocalDate.now());

        var dateAfterTwoWeeks = LocalDateTime.now().plusDays(CarPark.BOOKING_PERIOD);
        if (bookingDate.isAfter(dateAfterTwoWeeks))
            throw new InvalidDateException("Bookings can only be made within a "
                    + CarPark.BOOKING_PERIOD + " day period from the current date: "
                    + LocalDate.now());
    }

    private long countBookingsOn(LocalDateTime date) {
        return this.bookings.stream()
                .filter(b -> b.getBookingDate().compareTo(date.toLocalDate()) == 0)
                .count();
    }

    @Override
    public String toString() {
        return "CarPark{" +
                "\n\tOrganization=\"" + organization + '\"' +
                "\n\tbookings=" + bookings +
                "\n}";
    }
}
