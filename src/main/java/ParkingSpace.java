import payment.Money;

public class ParkingSpace {
    private final int spaceNumber;
    private final Money price;

    ParkingSpace(int spaceNumber, Money price){
        this.spaceNumber = spaceNumber;
        this.price = price;
    }

    public Money getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ParkingSpace{" +
                "spaceNumber=" + spaceNumber +
                ", price=" + price +
                '}';
    }
}
