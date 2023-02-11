//import BookingDateValidator;
//import CarPark;
import exception.InvalidDateException;
import exception.InvalidDateFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class BookingDateValidatorTest {
    DateTimeFormatter dateTimeFormatter;

    @BeforeEach
    void init(){
        final String DATE_FORMAT = "dd-MM-yyyy";
        dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    }

    @Nested
    @DisplayName("Given a booking date")
    class FormatTests {
        @Test
        @DisplayName("returns a valid booking date if it's a correctly formatted.")
        void returnAValidLocalDateTimeGivenACorrectDate() {
            LocalDate today = LocalDate.now();

            String date = today.format(dateTimeFormatter);
            String expected = today + "T08:00";

            LocalDateTime bookingDate = assertDoesNotThrow(() -> BookingDateValidator.validate(date));
            assertEquals(expected, bookingDate.toString());
        }

        @Test
        @DisplayName("throws an InvalidDateFormatException exception if it's incorrectly formatted")
        void throwsExceptionIfDateFormatIsInCorrectDate() {
            String date = LocalDate.now().toString();

            InvalidDateFormatException thrown = assertThrows(InvalidDateFormatException.class,
                    () -> BookingDateValidator.validate(date));

            assertEquals("Unable to create date time from: ["+ date + "], please enter date with format [dd-MM-yyyy].", thrown.getMessage());
        }
    }

    @Nested
    @DisplayName("Given a well formatted date")
    class DateValidityTests{
        @Test
        @DisplayName("return date that is valid if it is a valid booking date")
        void returnValidDate(){
            LocalDate threeDaysFromToday = LocalDate.now().plusDays(3);
            String date = threeDaysFromToday.format(dateTimeFormatter);

            LocalDateTime bookingDate = assertDoesNotThrow(() -> BookingDateValidator.validate(date));
            assertEquals(threeDaysFromToday, bookingDate.toLocalDate());
        }

        @Test
        @DisplayName("throw an InvalidDateException exception if it's an old date")
        void throwsExceptionGivenAnOldDate() {
            LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
            String date = threeDaysAgo.format(dateTimeFormatter);

            InvalidDateException thrown = assertThrows(InvalidDateException.class,
                    () -> BookingDateValidator.validate(date));

            assertEquals("Booking date: " + threeDaysAgo + " can not be before the current date: " + LocalDate.now(), thrown.getMessage());
        }

        @Test
        @DisplayName("throw InvalidDateException exception if the date is beyond the two week booking period")
        void throwsExceptionGivenADateBeyondTheTwoWeekBookingPeriod() {
            LocalDate thirtyDaysLater = LocalDate.now().plusDays(30);
            String date = thirtyDaysLater.format(dateTimeFormatter);

            InvalidDateException thrown = assertThrows(InvalidDateException.class,
                    () -> BookingDateValidator.validate(date));

            assertEquals("Bookings can only be made within a "
                    + CarPark.BOOKING_PERIOD + " day period from the current date: "
                    + LocalDate.now(), thrown.getMessage());
        }
    }
}
