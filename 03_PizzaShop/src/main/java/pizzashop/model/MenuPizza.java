package pizzashop.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class MenuPizza {
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty price;

    public MenuPizza(String mName, Double mPrice) {
        this.name = new SimpleStringProperty(mName);
        this.price = new SimpleDoubleProperty(mPrice);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    @Override
    public String toString() {
        return name + "," + price;
    }
}
