package pizzashop.service;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import pizzashop.model.PaymentType;

import java.util.Optional;

public class PaymentAlert  {
    private Service service;

    public PaymentAlert(Service service){
        this.service=service;
    }

    public boolean showPaymentAlert(int tableNumber, double totalAmount ) throws Exception {
        Alert paymentAlert = new Alert(Alert.AlertType.CONFIRMATION);
        paymentAlert.setTitle("Payment for Table "+tableNumber);
        paymentAlert.setHeaderText("Total amount: " + totalAmount);
        paymentAlert.setContentText("Please choose payment option");
        ButtonType cardPayment = new ButtonType("Pay by Card");
        ButtonType cashPayment = new ButtonType("Pay Cash");
        ButtonType cancel = new ButtonType("Cancel");
        paymentAlert.getButtonTypes().setAll(cardPayment, cashPayment, cancel);
        Optional<ButtonType> result = paymentAlert.showAndWait();
        if(!result.isPresent()){
            return false;
        }
        if (result.get() == cardPayment) {
            service.makePayment(tableNumber, PaymentType.Card);
            return true;
        } else if (result.get() == cashPayment) {
            service.makePayment(tableNumber, PaymentType.Cash);
            return true;
        } else {
            return false;
        }
    }
}
