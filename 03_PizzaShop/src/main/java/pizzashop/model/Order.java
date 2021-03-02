package pizzashop.model;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private final Integer tableNr;
    private Map<String,OrderPizza> orderList;
    private Double totalPrice;

    public Order(Integer tableNr) {
        this.tableNr = tableNr;
        this.orderList = new HashMap<>();
        this.totalPrice = 0.0;
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

    public void addToOrder(OrderPizza orderPizza) {
        String pizzaName = orderPizza.getName();
        if (this.orderList.containsKey(pizzaName)) {
            this.orderList.get(pizzaName).setQuantity(this.orderList.get(pizzaName).getQuantity() + orderPizza.getQuantity());
        }
        else {
            this.orderList.put(pizzaName, orderPizza);
        }

        this.totalPrice += orderPizza.getPrice();
    }
}
