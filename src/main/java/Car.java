public class Car {
    private final String licenceNumber;

    public Car(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    @Override
    public String toString() {
        return "Car{" +
                "licenceNumber='" + licenceNumber + '\'' +
                '}';
    }
}
