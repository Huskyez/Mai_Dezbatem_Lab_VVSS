package pizzashop.repository;

import pizzashop.model.Order;

import java.util.HashMap;
import java.util.Map;

public class OrderRepository {
    private Map<Integer, Order> orderMap;

    public OrderRepository() {
        this.orderMap = new HashMap<>();
    }

    public Order getOrder(Integer tableNr) {
        return this.orderMap.get(tableNr);
    }

    public void addOrder(Order order) {
        this.orderMap.put(order.getTableNr(), order);
    }

    public void removeOrder(Integer tableNr) {
        this.orderMap.remove(tableNr);
    }
}
