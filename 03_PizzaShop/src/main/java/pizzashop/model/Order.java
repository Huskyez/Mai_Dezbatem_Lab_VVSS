package pizzashop.model;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private final Integer tableNr;
    private Map<String,OrderPizza> orderList;
    private Double totalPrice;
    private OrderState state;

    public Order(Integer tableNr) {
        this.tableNr = tableNr;
        this.orderList = new HashMap<>();
        this.totalPrice = 0.0;
        this.state = OrderState.WAITING;
    }

    public Integer getTableNr() {
        return tableNr;
    }

    public Map<String, OrderPizza> getOrderList() {
        return orderList;
    }

    public void setOrderList(Map<String, OrderPizza> orderList) {
        this.orderList = orderList;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void addToOrder(OrderPizza orderPizza) {
        String pizzaName = orderPizza.getName();
        if (this.orderList.containsKey(pizzaName)) {
            OrderPizza currentOrderPizza = this.orderList.get(pizzaName);
            currentOrderPizza.setQuantity(currentOrderPizza.getQuantity() + orderPizza.getQuantity());
            currentOrderPizza.setPrice(currentOrderPizza.getPrice() + orderPizza.getPrice());
        }
        else {
            this.orderList.put(pizzaName, orderPizza);
        }

        this.totalPrice += orderPizza.getPrice();
    }

    @Override
    public String toString() {
        return "Table " + tableNr;
    }
}
