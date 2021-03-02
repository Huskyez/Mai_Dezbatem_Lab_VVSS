package pizzashop.service;

import pizzashop.model.*;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.OrderRepository;
import pizzashop.repository.PaymentRepository;

import java.util.List;

public class Service {

    private final MenuRepository menuRepo;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public Service(MenuRepository menuRepo, OrderRepository orderRepository, PaymentRepository paymentRepository){
        this.menuRepo=menuRepo;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<MenuPizza> getMenuData(){ return menuRepo.getMenu(); }
    public Order getOrder(int tableNr) {
        return orderRepository.getOrder(tableNr);
    }

    public void addToOrder(OrderPizza orderPizza, Integer tableNr) {
        Order currentOrder = this.orderRepository.getOrder(tableNr);
        if (currentOrder == null) {
            Order order = new Order(tableNr);
            order.addToOrder(orderPizza);
            this.orderRepository.addOrder(order);
        }
        else {
            currentOrder.addToOrder(orderPizza);
        }
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
}
