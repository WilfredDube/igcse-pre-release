package payment.method;

public enum PaymentType {
    ECOCASH("ecocash"), CREDITCARD("creditcard"), ONEMONEY("onemoney");

    private final String name;

    PaymentType(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

