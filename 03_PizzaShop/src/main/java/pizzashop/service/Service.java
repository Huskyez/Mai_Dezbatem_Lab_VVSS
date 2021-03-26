package pizzashop.service;

import pizzashop.model.*;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.OrderRepository;
import pizzashop.repository.PaymentRepository;

import javax.sql.rowset.serial.SerialException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Service extends Observable{
    public static final String INVALID_QUANTITY_MESSAGE = "Quantity must be greater than 0";
    public static final String INVALID_NAME_MESSAGE = "Pizza's name is not valid";
    public static final String INVALID_DATE_MESSAGE = "Invalid Date";

    private final MenuRepository menuRepo;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public boolean isKitchenClosed = false;

    public Service(MenuRepository menuRepo, OrderRepository orderRepository, PaymentRepository paymentRepository){
        this.menuRepo=menuRepo;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<MenuPizza> getMenuData(){ return menuRepo.getMenu(); }
    public Order getOrder(int tableNr) {
        return orderRepository.getOrder(tableNr);
    }

    public void addToOrder(OrderPizza orderPizza, Integer tableNr) throws PizzaException {

        if(orderPizza.getQuantity()<1){
            throw new PizzaException(INVALID_QUANTITY_MESSAGE);
        }

        Optional<MenuPizza> myName = this.getMenuData().stream().filter(x -> orderPizza.getName().equals(x.getName())).findFirst();

        if(!myName.isPresent()){
            throw new PizzaException(INVALID_NAME_MESSAGE);
        }

        Order currentOrder = this.orderRepository.getOrder(tableNr);
        if (currentOrder == null) {
            Order order = new Order(tableNr);
            order.addToOrder(orderPizza);
            this.orderRepository.addOrder(order);
        }
        else {
            currentOrder.addToOrder(orderPizza);
        }

        this.changeOrderState(tableNr, OrderState.WAITING);
    }

    public void makePayment(Integer tableNr, PaymentType type) throws Exception {
        Order currentOrder = this.orderRepository.getOrder(tableNr);

        if (currentOrder != null) {
            this.orderRepository.removeOrder(tableNr);
            this.paymentRepository.writePayment(new Payment(tableNr, type, currentOrder.getTotalPrice()));

            return;
        }

        throw new Exception("Comanda nu a fost gasita");
    }

    public Double getTotalPrice(Integer tableNr) {
        try {
            return this.orderRepository.getOrder(tableNr).getTotalPrice();
        }
        catch (Exception e) {
            return 0.0;
        }
    }

    public void changeOrderState(int tableNr, OrderState state) {
        Order order = this.orderRepository.getOrder(tableNr);
        if (order != null) {
            order.setState(state);

            notifyAllObservers();
        }
    }

    public List<Order> getAllOrders() {
        return this.orderRepository.getAllOrders();
    }

    private List<Payment> getPaymentsByDate(LocalDate date) {
        return this.paymentRepository.readPayments().stream().filter(x -> x.getDate().equals(date)).collect(Collectors.toList());
    }

    public List<Payment> getPayments(LocalDate date, PaymentType type) throws PaymentException {
//        return this.getPaymentsByDate(date).stream().filter(x -> x.getType() == type).collect(Collectors.toList());

        if (date.isBefore(LocalDate.of(1970, 1, 1))) {
            throw new PaymentException(INVALID_DATE_MESSAGE);
        }

        List<Payment> payments = new ArrayList<>();

        for (Payment payment : paymentRepository.readPayments()) {
            if (payment.getDate().equals(date)) {
                if (payment.getType().equals(type)) {
                    payments.add(payment);
                }
            }
        }

        return payments;
    }

    public double calculateTotal(List<Payment> payments) {
        return payments.stream().map(Payment::getAmount).reduce(0.0, Double::sum);
    }

    public boolean isRestaurantEmpty() {
        return this.orderRepository.getAllOrders().isEmpty();
    }
}
