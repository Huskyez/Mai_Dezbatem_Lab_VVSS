package pizzashop.service;

public class PaymentException extends Exception {

    public PaymentException() {
    }

    public PaymentException(String message) {
        super(message);
    }
}
