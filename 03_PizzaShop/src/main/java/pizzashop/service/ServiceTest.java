package pizzashop.service;

import org.junit.jupiter.api.*;
import pizzashop.model.Order;
import pizzashop.model.OrderPizza;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.OrderRepository;
import pizzashop.repository.PaymentRepository;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    private static Service srv;
    @BeforeAll
    public static void prepareObject(){
        OrderRepository orderRepo = new OrderRepository();
        MenuRepository menuRepo = new MenuRepository();
        PaymentRepository payRepo = new PaymentRepository();

        srv = new Service(menuRepo, orderRepo, payRepo);
    }

    @AfterEach
    public void clear(){
        OrderRepository orderRepo = new OrderRepository();
        MenuRepository menuRepo = new MenuRepository();
        PaymentRepository payRepo = new PaymentRepository();

        srv = new Service(menuRepo, orderRepo, payRepo);    }

    @Test
    @Tag("ECP")
    public void TC1_ECP_all_valid(){
        double price = 11.00;
        int quantity = 4;
        String name = "Calzone";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            srv.addToOrder(orderPizza, 5);
            Order ord = srv.getOrder(5);
            assertEquals(quantity, (int) ord.getOrderList().get(name).getQuantity());
            assertEquals(ord.getOrderList().get(name).getPrice(), price * quantity);
        } catch (PizzaException e) {
            fail();
        }
    }



    @Test
    @Tag("ECP")
    public void TC3_ECP_name_invalid(){
        double price = 11.00;
        int quantity = 4;
        String name = "Roman Pitesti";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            srv.addToOrder(orderPizza, 5);
            fail();

        } catch (PizzaException e) {
            assertEquals("Pizza's name is not valid", e.getMessage());
        }
    }

    @Test
    @Tag("ECP")
    public void TC5_ECP_quantity_invalid(){
        double price = 11.00;
        int quantity = -67;
        String name = "Quatro Stagioni";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            srv.addToOrder(orderPizza, 5);
            fail();

        } catch (PizzaException e) {
            assertEquals("Quantity must be greater than 0", e.getMessage());
        }
    }

    @Timeout(1)
    @DisplayName("First BVA")
    // quantity = 1
    @Test
    @Tag("BVA")
    public void TC1_BVA(){
        double price = 11.00;
        int quantity = 1;
        String name = "Calzone";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            srv.addToOrder(orderPizza, 5);
            Order ord = srv.getOrder(5);
            assertEquals(quantity, (int) ord.getOrderList().get(name).getQuantity());
            assertEquals(ord.getOrderList().get(name).getPrice(), price * quantity);
        } catch (PizzaException e) {
            fail();
        }
    }

    // quantity = -1
    @Test
    @Tag("BVA")
    public void TC2_BVA(){
        double price = 11.00;
        int quantity = -1;
        String name = "Calzone";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            srv.addToOrder(orderPizza, 5);
            fail();
        } catch (PizzaException e) {
            assertEquals("Quantity must be greater than 0", e.getMessage());
        }
    }

    // quantity = 0
    @Test
    @Tag("BVA")
    public void TC3_BVA(){
        double price = 11.00;
        int quantity = 0;
        String name = "Calzone";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            srv.addToOrder(orderPizza, 5);
            fail();
        } catch (PizzaException e) {
            assertEquals("Quantity must be greater than 0", e.getMessage());
        }
    }

    // quantity = max-1
    @Test
    @Tag("BVA")
    public void TC4_BVA(){
        double price = 11.00;
        int quantity = Integer.MAX_VALUE-1;
        String name = "Calzone";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            srv.addToOrder(orderPizza, 5);
            Order ord = srv.getOrder(5);
            assertEquals(quantity, (int) ord.getOrderList().get(name).getQuantity());
            assertEquals(ord.getOrderList().get(name).getPrice(), price * quantity);
        } catch (PizzaException e) {
            fail();
        }
    }

    // quantity = max
    @Test
    @Tag("BVA")
    public void TC5_BVA(){
        double price = 11.00;
        int quantity = Integer.MAX_VALUE;
        String name = "Calzone";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            srv.addToOrder(orderPizza, 5);
            Order ord = srv.getOrder(5);
            assertEquals(quantity, (int) ord.getOrderList().get(name).getQuantity());
            assertEquals(ord.getOrderList().get(name).getPrice(), price * quantity);
        } catch (PizzaException e) {
            fail();
        }
    }

    // quantity = max + 1
    @RepeatedTest(1)
    @Test
    @Tag("BVA")
    public void TC6_BVA(){
        double price = 11.00;
        int quantity = Integer.MAX_VALUE + 1;
        String name = "Calzone";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            srv.addToOrder(orderPizza, 5);
            fail();
        } catch (PizzaException e) {
            assertEquals("Quantity must be greater than 0", e.getMessage());
        }
    }

}