import exception.InvalidDateException;
import exception.InvalidDateFormatException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookingDateValidator {
    public static final String DATE_FORMAT = "dd-MM-yyyy h:mm a";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static LocalDateTime validate(String date) throws InvalidDateFormatException, InvalidDateException {
        LocalDateTime bookingDate = getLocalDateTime(date);

        try {
            validateBookingDate(bookingDate);
        } catch (InvalidDateException e) {
            throw new InvalidDateException(e.getMessage());
        }

        return bookingDate;
    }

    private static LocalDateTime getLocalDateTime(String date) throws InvalidDateFormatException {
        LocalDateTime bookingDate;
        try {
            bookingDate = LocalDateTime.parse(date.toUpperCase() + " 8:00 AM", dateTimeFormatter);
        } catch (Exception e) {
            throw new InvalidDateFormatException("Unable to create date time from: [" + date.toUpperCase()
                    + "], please enter date with format [dd-MM-yyyy].");
        } return bookingDate;
    }

    private static void validateBookingDate(LocalDateTime bookingDate) throws InvalidDateException {
        if (bookingDate.toLocalDate().isBefore(LocalDate.now()))
            throw new InvalidDateException("Booking date: " + bookingDate.toLocalDate()
                    + " can not be before the current date: "
                    + LocalDate.now());

        var dateAfterTwoWeeks = LocalDateTime.now().plusDays(CarPark.BOOKING_PERIOD);
        if (bookingDate.isAfter(dateAfterTwoWeeks))
            throw new InvalidDateException("Bookings can only be made within a "
                    + CarPark.BOOKING_PERIOD + " day period from the current date: "
                    + LocalDate.now());
    }
}
