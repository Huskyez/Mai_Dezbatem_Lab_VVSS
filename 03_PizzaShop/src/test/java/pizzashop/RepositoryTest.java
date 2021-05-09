package pizzashop;

import org.junit.jupiter.api.Test;
import pizzashop.model.Order;
import pizzashop.repository.OrderRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositoryTest {
    @Test
    public void orderRepo_addOrder_test () {
        OrderRepository orderRepository = new OrderRepository();

        orderRepository.addOrder(new Order(1));

        assertEquals(orderRepository.getOrder(1).getTableNr(), 1);
        assertEquals(orderRepository.getAllOrders().size(), 1);
    }

    @Test
    public void orderRepo_removeOrder_test() {
        OrderRepository orderRepository = new OrderRepository();

        orderRepository.addOrder(new Order(1));
        assertEquals(orderRepository.getAllOrders().size(), 1);

        orderRepository.removeOrder(1);
        assertEquals(orderRepository.getAllOrders().size(), 0);
    }
}
