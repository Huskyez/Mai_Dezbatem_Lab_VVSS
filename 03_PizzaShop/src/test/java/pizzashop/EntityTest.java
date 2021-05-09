package pizzashop;

import org.junit.jupiter.api.*;
import pizzashop.model.*;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EntityTest {
    @Test
    public void menuPizza_test(){
        MenuPizza menuPizza = new MenuPizza("pizza", 24.0);

        assertEquals(menuPizza.getName(), "pizza");
        assertEquals(menuPizza.getPrice(), 24.0);
    }

    @Test
    public void payment_test() {
        Payment payment = new Payment(1, PaymentType.Card, 33.3);

        assertEquals(payment.getTableNumber(), 1);
        assertEquals(payment.getAmount(), 33.3);
        assertEquals(payment.getType(), PaymentType.Card);
        assertEquals(payment.getDate(), LocalDate.now());
    }
}
