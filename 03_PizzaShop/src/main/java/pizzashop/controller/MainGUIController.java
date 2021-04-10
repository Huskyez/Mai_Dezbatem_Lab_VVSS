package pizzashop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import  javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pizzashop.gui.OrdersGUI;
import pizzashop.model.Order;
import pizzashop.model.OrderState;
import pizzashop.model.PaymentType;
import pizzashop.service.Observer;
import pizzashop.service.PaymentException;
import pizzashop.service.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MainGUIController implements Observer {
    @FXML
    private MenuItem help;
    @FXML
    private VBox vBox;

    private List<Button> tablesButtons = new ArrayList<>();

    Service service;
    private final int NUMBER_TABLES = 8;

    public MainGUIController(){}

    public void setService(Service service){
        this.service=service;
        this.service.add(this);
        tableHandlers(NUMBER_TABLES);
    }

    private void tableHandlers(int nr){
        for(int i = 0; i < nr; i++) {
            Button button = new Button("Table " + (i+1));
            button.setStyle("-fx-background-color: green");
            tablesButtons.add(button);
            final int x = i;
            button.setOnAction(event -> {
                        OrdersGUI ordersGUI = new OrdersGUI();
                        ordersGUI.setTableNumber(x+1);
                        ordersGUI.displayOrdersForm(service);
                    });
            vBox.getChildren().add(button);
        }
    }


    public void initialize(){
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        help.setOnAction((ActionEvent event) -> {
            Stage stage = new Stage();

            stage.setTitle("How to use..");
            final Group rootGroup = new Group();
            final Scene scene = new Scene(rootGroup, 600, 250);
            final Text text1 = new Text(
                    25, 25,
                    "1. Click on a Table button - a table order form will pop up. "+ System.lineSeparator()
                    +System.lineSeparator()+
                            "2.Choose a Menu item from the menu list, set Quantity from spinner, " +  System.lineSeparator()
                            +"press Add to order button and Click on the Menu list (Repeat)" + System.lineSeparator()
                    +System.lineSeparator()+
                            "3.Press Place order button, the order will appear in the Kitchen window"+ System.lineSeparator()
                    +System.lineSeparator()+
                            "4.On the Kitchen window Click on the order in the Orders List and Press the Ready button, " + System.lineSeparator()
                    +System.lineSeparator()+
                             "5.On the Table order form press the Make Payment button"+ System.lineSeparator()
            );

            text1.setFont(Font.font(java.awt.Font.SERIF, 15 ) );
            rootGroup.getChildren().add(text1 );

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
             });
    }

    public void showPayments() {

        try {
            double cardTotal = this.service.calculateTotal(this.service.getPayments(LocalDate.now(), PaymentType.Card));
            double cashTotal = this.service.calculateTotal(this.service.getPayments(LocalDate.now(), PaymentType.Cash));
            Alert alert = new Alert(Alert.AlertType.INFORMATION, String.format("Card: %f\nCash: %f", cardTotal, cashTotal));
            alert.showAndWait();
        } catch (PaymentException e) {
            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.ERROR, String.format("Card: %f\nCash: %f", cardTotal, cashTotal));
//            alert.showAndWait();
        }


    }

    @Override
    public void update() {
        for(int i = 0; i < NUMBER_TABLES; i++) {
            String style = "-fx-background-color: ";
            Order order = this.service.getOrder(i + 1);
            if (order == null) {
                style += "green";
            }
            else if (order.getState().equals(OrderState.READY)){
                style += "yellow";
            }
            else {
                style += "red";
            }
            tablesButtons.get(i).setStyle(style);
        }
    }
}
