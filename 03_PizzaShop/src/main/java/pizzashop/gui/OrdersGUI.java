package pizzashop.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pizzashop.controller.OrdersGUIController;
import pizzashop.service.Service;

import java.io.IOException;


public class OrdersGUI {

    protected int tableNumber;
    public int getTableNumber() {
        return tableNumber;
    }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }
    private Service service;

    public void displayOrdersForm(Service service){
     SplitPane splitPane = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OrdersGUIFXML.fxml"));

            //splitPane = FXMLLoader.load(getClass().getResource("/fxml/OrdersGUIFXML.fxml"));
            splitPane = loader.load();

            Stage orderStage = new Stage();
            orderStage.setTitle("Table"+getTableNumber()+" order form");
            orderStage.setResizable(false);
            // disable X on the window
            orderStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    // consume event
                    event.consume();
                }
            });
            orderStage.setScene(new Scene(splitPane));
            orderStage.show();


            OrdersGUIController ordersCtrl= loader.getController();
            ordersCtrl.setService(service, tableNumber);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
