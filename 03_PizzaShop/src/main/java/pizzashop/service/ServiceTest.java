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
    public void addToOrder_allValid_addedToOrder(){
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
    public void addToOrder_nameInvalid_exception(){
        double price = 11.00;
        int quantity = 4;
        String name = "Roman Pitesti";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            srv.addToOrder(orderPizza, 5);
            fail();

        } catch (PizzaException e) {
            assertEquals(Service.INVALID_NAME_MESSAGE, e.getMessage());
        }
    }

    @Test
    @Tag("ECP")
    public void addToOrder_quantityInvalid_exception(){
        double price = 11.00;
        int quantity = -67;
        String name = "Quatro Stagioni";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            srv.addToOrder(orderPizza, 5);
            fail();

        } catch (PizzaException e) {
            assertEquals(Service.INVALID_QUANTITY_MESSAGE, e.getMessage());
        }
    }

    @Timeout(1)
    @DisplayName("First BVA")
    // quantity = 1
    @Test
    @Tag("BVA")
    public void addToOrder_quantity1_addedToOrder(){
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
    public void addToOrder_quantityMinus1_exception(){
        double price = 11.00;
        int quantity = -1;
        String name = "Calzone";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            srv.addToOrder(orderPizza, 5);
            fail();
        } catch (PizzaException e) {
            assertEquals(Service.INVALID_QUANTITY_MESSAGE, e.getMessage());
        }
    }

    // quantity = 0
    @Test
    @Tag("BVA")
    public void addToOrder_quantity0_exception(){
        double price = 11.00;
        int quantity = 0;
        String name = "Calzone";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            srv.addToOrder(orderPizza, 5);
            fail();
        } catch (PizzaException e) {
            assertEquals(Service.INVALID_QUANTITY_MESSAGE, e.getMessage());
        }
    }

    // quantity = max-1
    @Test
    @Tag("BVA")
    public void addToOrder_quantityMaxMinus1_addedToOrder(){
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
    public void addToOrder_quantityMax_addedToOrder(){
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
    @Tag("BVA")
    public void addToOrder_quantityMaxPlus1_exception(){
        double price = 11.00;
        int quantity = Integer.MAX_VALUE + 1;
        String name = "Calzone";
        OrderPizza orderPizza = new OrderPizza(name, price, quantity);
        try {
            srv.addToOrder(orderPizza, 5);
            fail();
        } catch (PizzaException e) {
            assertEquals(Service.INVALID_QUANTITY_MESSAGE, e.getMessage());
        }
    }

    @Test
    @Disabled
    public void disabled() {
        System.out.println("disabled");
    }
}