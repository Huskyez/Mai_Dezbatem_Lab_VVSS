package pizzashop.model;

public class OrderPizza {
    private Integer quantity;
    private final MenuPizza menuPizza;

    public OrderPizza(Integer quantity, MenuPizza menuPizza) {
        this.quantity = quantity;
        this.menuPizza = menuPizza;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public MenuPizza getMenuPizza() {
        return menuPizza;
    }
}
