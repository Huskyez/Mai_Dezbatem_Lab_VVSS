package pizzashop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import pizzashop.model.Order;
import pizzashop.model.OrderPizza;
import pizzashop.model.OrderState;
import pizzashop.model.PaymentType;
import pizzashop.service.Observer;
import pizzashop.service.Service;

import java.util.stream.Collectors;


public class KitchenGUIController implements Observer {
    @FXML
    private ListView<Order> ordersListView;

    @FXML
    private ListView<OrderPizza> pizzasListView;

    @FXML
    private Button readyButton;

    @FXML
    void handleReady(ActionEvent event) {
        Order selectedOrder = ordersListView.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an order!");
            alert.showAndWait();
            return;
        }

        orders.remove(selectedOrder);
        this.ordersListView.setItems(orders);

        pizzas.clear();
        this.pizzasListView.setItems(pizzas);

        this.service.changeOrderState(selectedOrder.getTableNr(), OrderState.READY);
    }

    public static  ObservableList<Order> orders = FXCollections.observableArrayList();
    public static  ObservableList<OrderPizza> pizzas = FXCollections.observableArrayList();
//    private Object selectedOrder;
//    private Calendar now = Calendar.getInstance();
//    private String extractedTableNumberString = new String();
//    private int extractedTableNumberInteger;

    private Service service;

    public void initialize() {
        pizzasListView.setSelectionModel(null);
        ordersListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Order selectedOrder = ordersListView.getSelectionModel().getSelectedItem();
                if (selectedOrder == null) {
                    return;
                }

                pizzas.setAll(selectedOrder.getOrderList().values());
                pizzasListView.setItems(pizzas);
            }
        });
    }

    public void setService(Service service) {
        this.service = service;
        this.service.add(this);
    }

    @Override
    public void update() {
        orders.setAll(this.service.getAllOrders().stream().filter(x -> x.getState() == OrderState.RECEIVED).collect(Collectors.toList()));
        ordersListView.setItems(orders);
    }

    public boolean isRestaurantEmpty() {
        return this.service.isRestaurantEmpty();
    }
}