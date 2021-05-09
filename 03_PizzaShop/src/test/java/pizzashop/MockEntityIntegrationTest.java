package pizzashop;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import pizzashop.model.Order;
import pizzashop.model.OrderPizza;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.OrderRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PaymentException;
import pizzashop.service.PizzaException;
import pizzashop.service.Service;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

public class MockEntityIntegrationTest {
    @Test
    public void orderPizzaMock_RepoServiceIntegration_addToOrder_valid() {
        Service service = new Service(new MenuRepository(), new OrderRepository(), new PaymentRepository());

        double price = 11.00;
        int quantity = 4;
        String name = "Calzone";

        OrderPizza orderPizza = mock(OrderPizza.class);
        Mockito.when(orderPizza.getName()).thenReturn(name);
        Mockito.when(orderPizza.getQuantity()).thenReturn(quantity);
        Mockito.when(orderPizza.getPrice()).thenReturn(price * quantity);

        try {
            service.addToOrder(orderPizza, 5);
            Order ord = service.getOrder(5);
            assertEquals(quantity, (int) ord.getOrderList().get(name).getQuantity());
            assertEquals(ord.getOrderList().get(name).getPrice(), price * quantity);
        } catch (PizzaException e) {
            fail();
        }
    }

    @Test
    public void paymentMock_RepoServiceIntegration_getPayments_valid() {
        Payment payment = mock(Payment.class);
        Mockito.when(payment.getDate()).thenReturn(LocalDate.of(2001, 3, 4));
        Mockito.when(payment.getType()).thenReturn(PaymentType.Cash);
        Mockito.when(payment.getAmount()).thenReturn(2.0);
        Mockito.when(payment.getTableNumber()).thenReturn(2);
        Mockito.when(payment.toString()).thenReturn("2,Cash,2.0,2001-03-04");

        PaymentRepository payRepo = new PaymentRepository("test.txt");
        payRepo.writePayment(payment, false);

        Service service = new Service(new MenuRepository(), new OrderRepository(), payRepo);

        try {
            List<Payment> result = service.getPayments(LocalDate.of(2001, 3, 4), PaymentType.Cash);
            assertEquals(result.size(), 1);
            assertEquals(result.get(0).getDate(), payment.getDate());
            assertEquals(result.get(0).getType(), payment.getType());
        } catch (PaymentException e) {
            e.printStackTrace();
            fail();
        }

    }
}
