package pizzashop.model;

public class OrderPizza extends MenuPizza{
    private Integer quantity;

    public OrderPizza(String mName, Double mPrice, Integer quantity) {
        super(mName, mPrice * quantity);
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
