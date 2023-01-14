package payment.method;

import payment.PaymentMethod;

public class PaymentMethodFactory {
    public static PaymentMethod getInstance(PaymentType paymentChoice) {
        PaymentMethod paymentMethod;

        switch (paymentChoice){
            case CREDITCARD:
                paymentMethod = new CreditCard(PaymentType.CREDITCARD.toString());
                break;
            case ECOCASH:
                paymentMethod = new Ecocash(PaymentType.ECOCASH.toString());
                break;
            case ONEMONEY:
                paymentMethod = new Onemoney(PaymentType.ONEMONEY.toString());
                break;
            default:
                throw new RuntimeException("Wrong choice of payment system. Select values from 1 to 3");
        }

        return paymentMethod;
    }
}
