package pizzashop.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import pizzashop.controller.KitchenGUIController;
import pizzashop.controller.OrdersGUIController;
import pizzashop.service.Service;

import java.io.IOException;
import java.util.Optional;


public class KitchenGUI {

    public KitchenGUI(Service service) {
        AnchorPane vBoxKitchen = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/kitchenGUIFXML.fxml"));
            vBoxKitchen = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Kitchen");
            stage.setResizable(false);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    if (((KitchenGUIController) loader.getController()).isRestaurantEmpty()) {
                        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to exit Kitchen window?", ButtonType.YES, ButtonType.NO);
                        Optional<ButtonType> result = exitAlert.showAndWait();
                        if (result.get() == ButtonType.YES){
                            //Stage stage = (Stage) this.getScene().getWindow();
                            service.isKitchenClosed = true;
                            stage.close();
                        }
                        // consume event
                        else if (result.get() == ButtonType.NO){
                            event.consume();
                        }
                        else {
                            event.consume();
                        }
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Restaurant is not empty");
                        alert.showAndWait();
                        event.consume();
                    }
                }

            });
            stage.setAlwaysOnTop(false);
            stage.setScene(new Scene(vBoxKitchen));
            stage.show();

            KitchenGUIController kitchenCtrl= loader.getController();
            kitchenCtrl.setService(service);
            stage.toBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

