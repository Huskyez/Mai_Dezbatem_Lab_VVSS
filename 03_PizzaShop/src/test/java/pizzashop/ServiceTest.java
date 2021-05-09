package pizzashop;

import org.junit.jupiter.api.Test;
import pizzashop.model.Order;
import pizzashop.model.OrderPizza;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.OrderRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaException;
import pizzashop.service.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ServiceTest {
    @Test
    public void addToOrder_allValid_addedToOrder(){
        Service service = new Service(new MenuRepository(), new OrderRepository(), new PaymentRepository());

        double price = 11.00;
        int quantity = 4;
        String name = "Calzone";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
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
    public void addToOrder_nameInvalid_exception(){
        Service service = new Service(new MenuRepository(), new OrderRepository(), new PaymentRepository());

        double price = 11.00;
        int quantity = 4;
        String name = "Roman Pitesti";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            service.addToOrder(orderPizza, 5);
            fail();

        } catch (PizzaException e) {
            assertEquals(Service.INVALID_NAME_MESSAGE, e.getMessage());
        }
    }
}
