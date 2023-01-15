import exception.InvalidDateException;
import exception.NoParkingSpaceException;
import exception.PaymentFailedException;
import payment.PaymentMethod;
import payment.method.PaymentMethodFactory;
import payment.method.PaymentType;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static final int NUM_OF_VISITORS = 3;

    public static void main(String[] args) {
        CarPark carPark = new CarPark("Motor space");

        for (int i = 0; i < NUM_OF_VISITORS; ) {
            Visitor newVisitor;
            Booking booking;
            LocalDateTime bookingDate;

            try {
                bookingDate = readBookingDate(carPark);
                newVisitor = createVisitor();

                booking = new Booking(newVisitor);

                carPark.addBooking(booking, bookingDate);
            } catch (NoParkingSpaceException | PaymentFailedException | IllegalArgumentException | ParseException | InvalidDateException e) {
                System.err.println("Error: " + e.getMessage());
                continue;
            }

            System.out.println("========================================================");
            i++;
        }

        System.out.println(carPark);
        System.out.println("========================================================");
    }

    private static Visitor createVisitor() throws IllegalArgumentException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your name: ");
        String name = scanner.nextLine();

        System.out.println("Enter your id number: ");
        String id = scanner.nextLine();

        System.out.println("Enter your address: ");
        String address = scanner.nextLine();

        System.out.println("Enter your licence number: ");
        String license = scanner.nextLine();

        System.out.println("We only allow 4 payment methods: " +
                "\n1. Credit/Debit card \n2. Ecocash \n3. Onemoney");
        System.out.println("Select your favourable payment method");
        String choice = scanner.next().toUpperCase();

        PaymentMethod paymentMethod = PaymentMethodFactory.getInstance(PaymentType.valueOf(choice));

        var newVisitor = new Visitor(name, id, address);
        Car car = new Car(license);
        newVisitor.setCar(car);
        newVisitor.setPaymentMethod(paymentMethod);

        return newVisitor;
    }

    private static LocalDateTime readBookingDate(CarPark carPark) throws ParseException, InvalidDateException {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime bookingDate;
        String pattern = "dd-MM-yyyy";

        System.out.println("Enter your most favourable booking date: ");
        String date = scanner.nextLine();

        carPark.validDateFormat(date);

        bookingDate = LocalDateTime.of(
                LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern)),
                LocalTime.now());

        carPark.validateBookingDate(bookingDate);

        return bookingDate;
    }
}
