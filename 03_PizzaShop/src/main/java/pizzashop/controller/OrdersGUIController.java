package pizzashop.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import pizzashop.model.MenuPizza;
import pizzashop.model.Order;
import pizzashop.model.OrderPizza;
import pizzashop.service.PaymentAlert;
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
        MenuPizza menuPizza = menuTable.getSelectionModel().getSelectedItem();

        Integer quantity = quantitySpinner.getValue();

        if (quantity == null || quantity < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Quantity is invalid!");
            alert.showAndWait();
        }
        else {
            if (menuPizza != null) {
                OrderPizza orderPizza = new OrderPizza(menuPizza.getName(), menuPizza.getPrice(), quantity);
                service.addToOrder(orderPizza, this.tableNumber);

                this.orderList.add(orderPizza);
                this.orderTable.setItems(orderList);

                this.totalPriceLabel.setText(this.service.getTotalPrice(tableNumber).toString());
            }
        }
    }

    @FXML
    void handleMakePayment(ActionEvent event) {

    }

    @FXML
    void handlePlaceOrder(ActionEvent event) {

    }

    private ObservableList<MenuPizza> menuData;
    private ObservableList<OrderPizza> orderList;

    private Service service;
    private int tableNumber;

//    public ObservableList<String> observableList;
//    private TableView<OrderPizza> table = new TableView<OrderPizza>();
//    private ObservableList<MenuPizza> menuData;// = FXCollections.observableArrayList();
//    private Calendar now = Calendar.getInstance();

    public OrdersGUIController(){ }

    public void setService(Service service, int tableNumber){
        this.service=service;
        this.tableNumber=tableNumber;
        initData();
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