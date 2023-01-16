import exception.*;
import payment.PaymentMethod;
import payment.method.PaymentMethodFactory;
import payment.method.PaymentType;

import java.time.LocalDateTime;
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
                bookingDate = readBookingDate();
                newVisitor = createVisitor();

                booking = new Booking(newVisitor);

                carPark.addBooking(booking, bookingDate);
            } catch (NoParkingSpaceException | PaymentFailedException | IllegalArgumentException |
                     InvalidDateException | InvalidDateFormatException | InvalidInputException e) {
                System.err.println("Error: " + e.getMessage());
                continue;
            }

            System.out.println("========================================================");
            i++;
        }

        System.out.println(carPark);
        System.out.println("========================================================");
    }

    private static Visitor createVisitor() throws IllegalArgumentException, InvalidInputException {
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
        System.out.println("Select a number corresponding to your favourable payment method");
        int choice = scanner.nextInt();

        PaymentMethod paymentMethod = PaymentMethodFactory.getInstance(getPaymentChoice(choice));

        var newVisitor = new Visitor(name, id, address);
        Car car = new Car(license);
        newVisitor.setCar(car);
        newVisitor.setPaymentMethod(paymentMethod);

        return newVisitor;
    }

    private static PaymentType getPaymentChoice(int choice) throws InvalidInputException {
        String paymentMethod;

        switch (choice){
            case 1: paymentMethod = "CREDITCARD";
                break;
            case 2: paymentMethod = "ECOCASH";
                break;
            case 3: paymentMethod = "ONEMONEY";
                break;
            default:
                throw new InvalidInputException("Incorrect choice");
        }
        return PaymentType.valueOf(paymentMethod);
    }

    private static LocalDateTime readBookingDate() throws InvalidDateException, InvalidDateFormatException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your most favourable booking date: ");
        String date = scanner.nextLine();

        return BookingDateValidator.validate(date);
    }
}
