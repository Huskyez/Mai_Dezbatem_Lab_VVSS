package pizzashop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pizzashop.model.MenuPizza;
import pizzashop.model.Order;
import pizzashop.model.OrderPizza;
import pizzashop.model.OrderState;
import pizzashop.service.PaymentAlert;
import pizzashop.service.PizzaException;
import pizzashop.service.Service;

public class OrdersGUIController {

    @FXML
    private TableView<MenuPizza> menuTable;

    @FXML
    private TableView<OrderPizza> orderTable;

    @FXML
    private Button addToOrderButton;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private Button makePaymentButton;

    @FXML
    private Button backButton;

    @FXML
    void handleBack(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleAddToOrder(ActionEvent event) {
        if (this.service.isKitchenClosed) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Kitchen is closed!");
            alert.showAndWait();
            this.handleBack(event);
            return;
        }

        MenuPizza menuPizza = menuTable.getSelectionModel().getSelectedItem();

        Integer quantity = quantitySpinner.getValue();

        if (quantity == null || quantity < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Quantity is invalid!");
            alert.showAndWait();
        }
        else {
            if (menuPizza != null) {
                Order order = this.service.getOrder(this.tableNumber);
                if (order != null && order.getState().equals(OrderState.READY)){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Order has already been placed!");
                    alert.showAndWait();
                    return;
                }
                OrderPizza orderPizza = new OrderPizza(menuPizza.getName(), menuPizza.getPrice(), quantity);
                try {
                    service.addToOrder(orderPizza, this.tableNumber);
                } catch (PizzaException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
                    alert.showAndWait();
                }

                this.orderList.setAll(this.service.getOrder(this.tableNumber).getOrderList().values());
                this.orderTable.setItems(orderList);

                this.totalPriceLabel.setText(this.service.getTotalPrice(tableNumber).toString());
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Must select a pizza from menu!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void handleMakePayment(ActionEvent event) {
        Order order = this.service.getOrder(this.tableNumber);

        if (order != null && order.getState() == OrderState.READY) {
            try {
                boolean isPaid = paymentAlert.showPaymentAlert(tableNumber, order.getTotalPrice());
                if (isPaid) {
                    this.service.notifyAllObservers();
                    this.handleBack(event);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Order must be ready!");
            alert.showAndWait();
        }
    }

    @FXML
    void handlePlaceOrder(ActionEvent event) {
        Order order = this.service.getOrder(this.tableNumber);
        if (order == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "There is no order!");
            alert.showAndWait();
            return;
        }
        if (order.getState().equals(OrderState.READY)){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Order has already been placed!");
            alert.showAndWait();
            return;
        }
        this.service.changeOrderState(this.tableNumber, OrderState.RECEIVED);
    }

    private ObservableList<MenuPizza> menuData;
    private ObservableList<OrderPizza> orderList;

    private Service service;
    private int tableNumber;

    private PaymentAlert paymentAlert;

//    public ObservableList<String> observableList;
//    private TableView<OrderPizza> table = new TableView<OrderPizza>();
//    private ObservableList<MenuPizza> menuData;// = FXCollections.observableArrayList();
//    private Calendar now = Calendar.getInstance();

    public OrdersGUIController(){ }

    public void setService(Service service, int tableNumber){
        this.service=service;
        this.tableNumber=tableNumber;
        initData();
        this.paymentAlert = new PaymentAlert(service);
    }

    private void initData(){
        menuData = FXCollections.observableArrayList(service.getMenuData());
        menuTable.setItems(menuData);

        Order order = service.getOrder(this.tableNumber);
        orderList = FXCollections.observableArrayList();
        if (order != null) {
            orderList.setAll(order.getOrderList().values());
            orderTable.setItems(orderList);

            totalPriceLabel.setText(order.getTotalPrice().toString());
        }

        //Controller for Place OrderPizza Button
//        placeOrder.setOnAction(event ->{
//            orderList= menuData.stream()
//                    .filter(x -> x.getQuantity()>0)
//                    .map(orderPizza -> orderPizza.getQuantity() +" "+ orderPizza.getName())
//                    .collect(Collectors.toList());
//            observableList = FXCollections.observableList(orderList);
//            KitchenGUIController.order.add("Table" + tableNumber +" "+ orderList.toString());
//            orderStatus.setText("OrderPizza placed at: " +  now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE));
//        });
//
//        //Controller for OrderPizza Served Button
//        orderServed.setOnAction(event -> {orderStatus.setText("Served at: " + now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE));
//        });
//
//        //Controller for Pay OrderPizza Button
//        payOrder.setOnAction(event -> {
//            orderPaymentList= menuData.stream()
//                    .filter(x -> x.getQuantity()>0)
//                    .map(orderPizza -> orderPizza.getQuantity()* orderPizza.getPrice())
//                    .collect(Collectors.toList());
//            setTotalAmount(orderPaymentList.stream().mapToDouble(e->e.doubleValue()).sum());
//            orderStatus.setText("Total amount: " + getTotalAmount());
//            System.out.println("--------------------------");
//            System.out.println("Table: " + tableNumber);
//            System.out.println("Total: " + getTotalAmount());
//            System.out.println("--------------------------");
//            PaymentAlert pay = new PaymentAlert(service);
//            pay.showPaymentAlert(tableNumber, this.getTotalAmount());
//        });
    }

    public void initialize(){
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
    }
}